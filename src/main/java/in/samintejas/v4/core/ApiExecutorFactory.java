package in.samintejas.v4.core;

import in.samintejas.v4.core.exceptions.ApiExecutorCreationException;
import in.samintejas.v4.core.model.ExecutorConfig;
import in.samintejas.v4.core.model.RestAPI;
import lombok.extern.log4j.Log4j2;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Log4j2
public class ApiExecutorFactory {

        private final Map<String, ApiExecutor> executorCache;
        private final ExecutorConfig defaultConfig;

        public ApiExecutorFactory() {
            this.executorCache = new ConcurrentHashMap<>();
            this.defaultConfig = ExecutorConfig.getDefault();
        }

        public ApiExecutorFactory(ExecutorConfig defaultConfig) {
            this.executorCache = new ConcurrentHashMap<>();
            this.defaultConfig = defaultConfig;
        }

        /**
         * Creates or retrieves a cached ApiExecutor instance based on the RestAPI configuration
         * and execution context.
         *
         * @param restAPI The REST API configuration
         * @param context The execution context
         * @return An ApiExecutor instance
         */
        public ApiExecutor createExecutor(RestAPI restAPI, ExecutionContext context) {

            validateInputs(restAPI, context);

            // For APIs that should be cached (like reference data APIs)
            if (shouldUseCache(restAPI)) {
                return getCachedExecutor(restAPI, context);
            }

            return createNewExecutor(restAPI, context);
        }

        private void validateInputs(RestAPI restAPI, ExecutionContext context) {
            if (restAPI == null) {
                throw new IllegalArgumentException("RestAPI configuration cannot be null");
            }
            if (context == null) {
                throw new IllegalArgumentException("ExecutionContext cannot be null");
            }
        }

        private boolean shouldUseCache(RestAPI restAPI) {
            // Check if the API is marked as cacheable in its configuration
            // This could be based on various factors like:
            // - Is it a reference data API?
            // - Is it a read-only API?
            // - Does it have specific caching requirements?
            return restAPI.isCacheable() || defaultConfig.isCacheEnabled();
        }

        private ApiExecutor getCachedExecutor(RestAPI restAPI, ExecutionContext context) {
            String cacheKey = generateCacheKey(restAPI);
            return executorCache.computeIfAbsent(cacheKey,
                    k -> createNewExecutor(restAPI, context));
        }

        private ApiExecutor createNewExecutor(RestAPI restAPI, ExecutionContext context) {
            try {
                ApiExecutor executor = new ApiExecutor(restAPI, context);
                configureExecutor(executor);
                return executor;
            } catch (Exception e) {
                log.error("Failed to create ApiExecutor for API: {}", restAPI.getName(), e);
                throw new ApiExecutorCreationException(
                        "Failed to create ApiExecutor: " + e.getMessage(), e);
            }
        }

        private void configureExecutor(ApiExecutor executor) {
            // Configure the executor with default or custom settings
            // This could include:
            // - Timeout settings
            // - Retry configurations
            // - Circuit breaker settings
            // - Metrics collection
            // - etc.
        }

        private String generateCacheKey(RestAPI restAPI) {
            // Generate a unique cache key based on the REST API configuration
            // This could include:
            // - API name
            // - Endpoint
            // - Method
            // - Other identifying characteristics
            return restAPI.getName() + "-" + restAPI.getUrl();
        }

        /**
         * Clears the executor cache.
         */
        public void clearCache() {
            executorCache.clear();
        }

        /**
         * Removes a specific executor from the cache.
         *
         * @param restAPI The REST API configuration
         */
        public void removeFromCache(RestAPI restAPI) {
            String cacheKey = generateCacheKey(restAPI);
            executorCache.remove(cacheKey);
        }

}
