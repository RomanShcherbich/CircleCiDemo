package steps;


import io.qameta.allure.Attachment;
import io.qameta.allure.Step;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class MainSteps {

    @Step("Business step for {keyword} ")
    public static void stepKeyword(String keyword) {
        System.out.println(keyword);
    }

    @Step("{description}")
    public static void step(String description) {
    }

    @Attachment(value = "Test data map", type = "application/json")
    public static byte[] stepWithAttachment(Map<String, String> map){
        String jsonString = "";
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry:
                map.entrySet()) {
            stringBuilder.append("\"");
            stringBuilder.append(entry.getKey());
            stringBuilder.append("\"");
            stringBuilder.append(":");
            stringBuilder.append("\"");
            stringBuilder.append(entry.getValue());
            stringBuilder.append("\"");
            stringBuilder.append(",");
        }
        jsonString = String.format("{%s}", stringBuilder);
        jsonString = jsonString.replace("\",}", "\"}");
        return jsonString.getBytes(StandardCharsets.UTF_8);
    }



}
