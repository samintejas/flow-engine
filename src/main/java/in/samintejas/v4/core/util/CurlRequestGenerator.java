package in.samintejas.v4.core.util;

import in.samintejas.v4.core.constants.HttpMethod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class CurlRequestGenerator {

    private static final Logger log = LogManager.getLogger("CurlLogger");

    public static void logCurlRequest(String curlCommand) {
        log.info(curlCommand);
    }


    public static String generatePostCurlRequest(String endpoint,HttpMethod httpMethod,String json , List<String> headers) {

        StringBuilder curlCommand = new StringBuilder("curl -X ")
                .append(httpMethod)
                .append(" \"").append(endpoint).append("\" ");

        if (headers != null && !headers.isEmpty()) {
            for (String header : headers) {
                curlCommand.append("-H \"").append(header).append("\" ");
            }
        }

        if (json != null && !json.isEmpty()) {
            curlCommand.append("-d '").append(json.replace("'", "\\'")).append("'");
        }

        return curlCommand.toString().trim();
    }

}
