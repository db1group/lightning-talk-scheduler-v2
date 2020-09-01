package com.db1group.ltscheduler;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
class LightningTalkSchedulerServiceTest {

    private static final String URL_SEND_MAIL = "https://graph.microsoft.com/v1.0/me/sendMail";

    @Mock
    private RestTemplate restTemplateMock;

    @InjectMocks
    private LightningTalkSchedulerService lightningTalkSchedulerService;

    @Test
    public void shouldTestUrl_forPostEntity() {
        when(restTemplateMock.postForEntity(anyString(), any(HttpEntity.class), eq(String.class))).thenReturn(ResponseEntity.accepted().build());

        lightningTalkSchedulerService.schedule();

        verify(restTemplateMock).postForEntity(eq(URL_SEND_MAIL), any(), any());
    }

    @Test
    public void shouldTestBody_forPosEntity() {
        when(restTemplateMock.postForEntity(anyString(), any(HttpEntity.class), eq(String.class))).thenReturn(ResponseEntity.accepted().build());

        LocalDateTime startDate = LocalDateTime.of(2020, 03, 25, 15, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2020, 03, 25, 15, 30, 0);
        lightningTalkSchedulerService.schedule("Refatorando seu código", startDate, endDate);

        ArgumentCaptor<HttpEntity> requestCaptor = ArgumentCaptor.forClass(HttpEntity.class);

        verify(restTemplateMock).postForEntity(eq(URL_SEND_MAIL), requestCaptor.capture(), any());
        HttpEntity capture = requestCaptor.getValue();

        Object body = capture.getBody();
        assertNotNull(body);
        assertEquals(getExpectedBody(), body.toString());
    }

    private Object getExpectedBody() {
        return "{\n" +
                "  \"message\": {\n" +
                "    \"subject\": \"Transmissão de Lightning Talk\",\n" +
                "    \"body\": {\n" +
                "      \"contentType\": \"Text\",\n" +
                "      \"content\": \"Solicito a transmissão da Lightning Talk \"Refatorando seu código\", que ocorrerá no dia 25/03/2020 das 15:00 até 15:30\"\n" +
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
    }


    @Test
    public void shouldSendTransmissionEmailToTheConfiguredAddress() {
        when(restTemplateMock.postForEntity(anyString(), any(HttpEntity.class), eq(String.class))).thenReturn(ResponseEntity.accepted().build());

        LocalDateTime startDate = LocalDateTime.of(2020, 03, 25, 15, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2020, 03, 25, 15, 30, 0);
        lightningTalkSchedulerService.schedule("Refatorando seu código", startDate, endDate);

        ArgumentCaptor<HttpEntity> requestCaptor = ArgumentCaptor.forClass(HttpEntity.class);

        verify(restTemplateMock).postForEntity(eq(URL_SEND_MAIL), requestCaptor.capture(), any());
        HttpEntity capture = requestCaptor.getValue();

        Object body = capture.getBody();
        assertNotNull(body);

        assertTrue(String.valueOf(body).contains("helpdesk@db1.com.br"));
    }


}