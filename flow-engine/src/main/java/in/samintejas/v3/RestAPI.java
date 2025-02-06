package in.samintejas.v3;

import in.samintejas.v3.util.DataFaker;
import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ToString
public class RestAPI {

    @Getter
    private final String name;
    @Getter
    private final String url;
    @Getter
    private final HttpMethod method;
    @Getter
    private final String template;

    // build with context


    public RestAPI(String name, String url, HttpMethod method, String template) {
        this.name = name;
        this.url = url;
        this.method = method;
        this.template = template;
    }

    public Map<String, String>  buildEmptyPlaceholderMap(){

        Map<String, String> paramMap = new HashMap<>();
        Pattern pattern = Pattern.compile("\\{\\{(.*?)\\}\\}");
        Matcher matcher = pattern.matcher(this.template);

        while (matcher.find()) {
            String placeholder = matcher.group(1);
            paramMap.put(placeholder, "");
        }

        return paramMap;

    }


    public String generateNewRequest(Map<String, String> params) {

        String filledTemplate = template;

        for (Map.Entry<String, String> entry : params.entrySet()) {
            String placeholder = "{{" + entry.getKey() + "}}";

            String value = "";
            if(entry.getKey().startsWith("$d-")) {
                value = DataFaker.generateRandomTime(entry.getKey().replace("$d-",""));
            } else if(entry.getKey().startsWith("$r-")) {
                value = DataFaker.generateRandomVal(entry.getKey().replace("$r-",""));
            } else {
                value = entry.getValue();
            }
            
            filledTemplate = filledTemplate.replace(placeholder, value);
        }
        
        return filledTemplate;
    }



}
