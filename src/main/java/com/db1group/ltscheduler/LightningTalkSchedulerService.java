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

        var transmissionEmailRequestBody = new TransmissionEmailRequestBody("ivo.batistela@db1.com.br", "Meet for lunch?", "The new cafeteria is open.");

        var request = new HttpEntity<TransmissionEmailRequestBody>(transmissionEmailRequestBody, headers);
        ResponseEntity<String> sendEmailResponse = restTemplate.postForEntity("https://graph.microsoft.com/v1.0/me/sendMail", request, String.class);

        return sendEmailResponse.getStatusCode() == HttpStatus.ACCEPTED;
    }
}
