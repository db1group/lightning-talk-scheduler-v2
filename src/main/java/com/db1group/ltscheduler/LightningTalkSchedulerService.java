package com.db1group.ltscheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LightningTalkSchedulerService {

    private RestTemplate restTemplate;

    @Autowired
    public LightningTalkSchedulerService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean schedule() {
        var headers = new HttpHeaders();
        headers.add("Authorization", "Bearer TOKEN");
        headers.add("Content-type", "application/json");

        String body = "{\n" +
                "  \"message\": {\n" +
                "    \"subject\": \"Meet for lunch?\",\n" +
                "    \"body\": {\n" +
                "      \"contentType\": \"Text\",\n" +
                "      \"content\": \"The new cafeteria is open.\"\n" +
                "    },\n" +
                "    \"toRecipients\": [\n" +
                "      {\n" +
                "        \"emailAddress\": {\n" +
                "          \"address\": \"ivo.batistela@db1.com.br\"\n" +
                "        }\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  \"saveToSentItems\": \"true\"\n" +
                "}";

        var request = new HttpEntity<String>(body, headers);
        ResponseEntity<String> sendEmailResponse = restTemplate.postForEntity("https://graph.microsoft.com/v1.0/me/sendMail", request, String.class);

        return sendEmailResponse.getStatusCode() == HttpStatus.ACCEPTED;
    }
}
