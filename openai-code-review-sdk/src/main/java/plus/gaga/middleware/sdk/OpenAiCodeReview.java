package plus.gaga.middleware.sdk;

import com.alibaba.fastjson2.JSON;
import plus.gaga.middleware.sdk.domain.ChatCompletionRequest;
import plus.gaga.middleware.sdk.domain.ChatCompletionSyncResponse;
import plus.gaga.middleware.sdk.domain.Model;
import plus.gaga.middleware.sdk.types.utils.BearerTokenUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class OpenAiCodeReview {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello world!");
        //1.代码检出
        ProcessBuilder processBuilder = new ProcessBuilder("git", "diff", "HEAD~1", "HEAD");
        //制定目录
        processBuilder.directory(new File("."));
        //执行
        Process process = processBuilder.start();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;

        StringBuilder diffCode = new StringBuilder();
        while ((line = bufferedReader.readLine()) !=null){
            diffCode.append(line);
        }
        int exitCode = process.waitFor();
        System.out.println("Exited with code = " + exitCode);
        System.out.println("diffCode.toString() = " + diffCode);

        //2. chatglm 代码评审
        String content = codeReview(diffCode.toString());
        System.out.println("content = " + content);
    }

    private static String codeReview(String diffCode) throws Exception {
        String apiKeySecret ="d4946eef67084eb29162a4c4f7c24961.rKibnmDHtCne2ScF";
        String token = BearerTokenUtils.getToken(apiKeySecret);
        URL url = new URL("https://open.bigmodel.cn/api/paas/v4/chat/completions");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization",token);
        connection.setRequestProperty("Content-Type","application/json");
        connection.setRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt");
        connection.setDoOutput(true);

        String code = "2+2";

        String jsonInpuString = "{"
                + "\"model\":\"glm-4-flash\","
                + "\"messages\": ["
                + "    {"
                + "        \"role\": \"user\","
                + "        \"content\": \"你是一个高级编程架构师，精通各类场景方案、架构设计和编程语言请，请您根据git diff记录，对代码做出评审。代码为: " + code + "\""
                + "    }"
                + "]"
                + "}";

        ChatCompletionRequest chatCompletionRequest = new ChatCompletionRequest();
        chatCompletionRequest.setModel(Model.GLM_4_FLASH.getCode());

        chatCompletionRequest.setMessages(new ArrayList<ChatCompletionRequest.Prompt>(){{
            add( new ChatCompletionRequest.Prompt("user","你是一个高级编程架构师，精通各类场景方案、架构设计和编程语言请，请您根据git diff记录，对代码做出评审。代码为:"));
            add( new ChatCompletionRequest.Prompt("user",diffCode));
        }});

        try (OutputStream os = connection.getOutputStream()){
            byte[] input = JSON.toJSONString(chatCompletionRequest).getBytes(StandardCharsets.UTF_8);
            os.write(input);
        }

        int responseCode = connection.getResponseCode();
        System.out.println(responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        connection.disconnect();

        ChatCompletionSyncResponse response = JSON.parseObject(content.toString(), ChatCompletionSyncResponse.class);
        return response.getChoices().get(0).getMessage().getContent();
    }

}