package com.db1group.ltscheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class LightningTalkSchedulerService {

    private static final String SCHEDULER_EMAIL_ADDRESS_EMAIL_TO = "scheduler.emailAddress.emailTo";

    private RestTemplate restTemplate;

    private Environment environment;

    @Autowired
    public LightningTalkSchedulerService(RestTemplate restTemplate, Environment environment) {
        this.restTemplate = restTemplate;
        this.environment = environment;
    }

    public boolean schedule() {
        LocalDateTime startDate = LocalDateTime.of(2020, 03, 25, 15, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2020, 03, 25, 15, 30, 0);
        return this.schedule("Refatorando seu código", startDate, endDate);
    }

    public boolean schedule(String ltTitle, LocalDateTime startDate, LocalDateTime endDate) {
        var headers = new HttpHeaders();
        headers.add("Authorization", "Bearer TOKEN");
        headers.add("Content-type", "application/json");

        String body = getBody(ltTitle, startDate, endDate, getEmailAddressToSend());

        var request = new HttpEntity<String>(body, headers);
        ResponseEntity<String> sendEmailResponse = restTemplate.postForEntity("https://graph.microsoft.com/v1.0/me/sendMail", request, String.class);

        return sendEmailResponse.getStatusCode() == HttpStatus.ACCEPTED;
    }

    private String getBody(String ltTitle, LocalDateTime startDate, LocalDateTime endDate, String emailAddressToSend) {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter dateTimeFormatterTime = DateTimeFormatter.ofPattern("HH:mm");
        String formattedStartDate = dateTimeFormatter.format(startDate);
        String formattedStartTime = dateTimeFormatterTime.format(startDate);
        String formattedEndTime = dateTimeFormatterTime.format(endDate);

        return "{\n" +
                "  \"message\": {\n" +
                "    \"subject\": \"Transmissão de Lightning Talk\",\n" +
                "    \"body\": {\n" +
                "      \"contentType\": \"Text\",\n" +
                "      \"content\": \"Solicito a transmissão da Lightning Talk \"" + ltTitle +"\", que ocorrerá no dia " + formattedStartDate + " das " + formattedStartTime + " até " + formattedEndTime + "\"\n" +
                "    },\n" +
                "    \"toRecipients\": [\n" +
                "      {\n" +
                "        \"emailAddress\": {\n" +
                "          \"address\": \"" + emailAddressToSend + "\"\n" +
                "        }\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  \"saveToSentItems\": \"true\"\n" +
                "}";
    }

    private String getEmailAddressToSend() {
        return environment.getProperty(SCHEDULER_EMAIL_ADDRESS_EMAIL_TO);
    }
}
