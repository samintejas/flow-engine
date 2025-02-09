package in.samintejas.v4.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecutorConfig {

    // Cache settings
    private boolean cacheEnabled;
    private Duration cacheExpiration;
    private int maxCacheSize;

    // Timeout settings
    private Duration connectTimeout;
    private Duration readTimeout;
    private Duration writeTimeout;

    // Retry settings
    private boolean retryEnabled;
    private int maxRetries;
    private Duration retryDelay;
    private double retryBackoffMultiplier;

    // Circuit breaker settings
    private boolean circuitBreakerEnabled;
    private int failureThreshold;
    private Duration resetTimeout;

    // Rate limiting
    private boolean rateLimitingEnabled;
    private int maxRequestsPerSecond;

    // Logging and monitoring
    private boolean metricsEnabled;
    private boolean detailedLogging;
    private String metricsPrefix;

    /**
     * Returns a default configuration instance with sensible defaults.
     */
    public static ExecutorConfig getDefault() {
        return ExecutorConfig.builder()
                // Cache defaults
                .cacheEnabled(true)
                .cacheExpiration(Duration.ofMinutes(5))
                .maxCacheSize(1000)

                // Timeout defaults
                .connectTimeout(Duration.ofSeconds(5))
                .readTimeout(Duration.ofSeconds(30))
                .writeTimeout(Duration.ofSeconds(30))

                // Retry defaults
                .retryEnabled(true)
                .maxRetries(3)
                .retryDelay(Duration.ofMillis(100))
                .retryBackoffMultiplier(1.5)

                // Circuit breaker defaults
                .circuitBreakerEnabled(true)
                .failureThreshold(5)
                .resetTimeout(Duration.ofSeconds(30))

                // Rate limiting defaults
                .rateLimitingEnabled(true)
                .maxRequestsPerSecond(100)

                // Monitoring defaults
                .metricsEnabled(true)
                .detailedLogging(false)
                .metricsPrefix("api.executor")
                .build();
    }

    /**
     * Creates a custom configuration builder with defaults pre-populated.
     */
    public static ExecutorConfigBuilder defaultBuilder() {
        ExecutorConfig defaults = getDefault();
        return ExecutorConfig.builder()
                .cacheEnabled(defaults.isCacheEnabled())
                .cacheExpiration(defaults.getCacheExpiration())
                .maxCacheSize(defaults.getMaxCacheSize())
                .connectTimeout(defaults.getConnectTimeout())
                .readTimeout(defaults.getReadTimeout())
                .writeTimeout(defaults.getWriteTimeout())
                .retryEnabled(defaults.isRetryEnabled())
                .maxRetries(defaults.getMaxRetries())
                .retryDelay(defaults.getRetryDelay())
                .retryBackoffMultiplier(defaults.getRetryBackoffMultiplier())
                .circuitBreakerEnabled(defaults.isCircuitBreakerEnabled())
                .failureThreshold(defaults.getFailureThreshold())
                .resetTimeout(defaults.getResetTimeout())
                .rateLimitingEnabled(defaults.isRateLimitingEnabled())
                .maxRequestsPerSecond(defaults.getMaxRequestsPerSecond())
                .metricsEnabled(defaults.isMetricsEnabled())
                .detailedLogging(defaults.isDetailedLogging())
                .metricsPrefix(defaults.getMetricsPrefix());
    }

    /**
     * Validates the configuration settings.
     * @throws IllegalArgumentException if any settings are invalid
     */
    public void validate() {
        if (maxCacheSize <= 0) {
            throw new IllegalArgumentException("Max cache size must be positive");
        }
        if (maxRetries < 0) {
            throw new IllegalArgumentException("Max retries cannot be negative");
        }
        if (failureThreshold <= 0) {
            throw new IllegalArgumentException("Failure threshold must be positive");
        }
        if (maxRequestsPerSecond <= 0) {
            throw new IllegalArgumentException("Max requests per second must be positive");
        }
        validateTimeouts();
    }

    private void validateTimeouts() {
        if (connectTimeout.isNegative()) {
            throw new IllegalArgumentException("Connect timeout cannot be negative");
        }
        if (readTimeout.isNegative()) {
            throw new IllegalArgumentException("Read timeout cannot be negative");
        }
        if (writeTimeout.isNegative()) {
            throw new IllegalArgumentException("Write timeout cannot be negative");
        }
    }

}
