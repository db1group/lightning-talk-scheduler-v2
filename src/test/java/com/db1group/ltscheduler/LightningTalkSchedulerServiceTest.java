package com.db1group.ltscheduler;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
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

        lightningTalkSchedulerService.schedule();

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
    }

}