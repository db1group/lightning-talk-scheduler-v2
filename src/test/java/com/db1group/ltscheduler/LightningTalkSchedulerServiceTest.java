package com.db1group.ltscheduler;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
class LightningTalkSchedulerServiceTest {

    private static final String URL_SEND_MAIL = "https://graph.microsoft.com/v1.0/me/sendMail";

    @MockBean
    private RestTemplate restTemplateMock;

    @Autowired
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

        TransmissionEmailRequestBody expectedRequestBody = new TransmissionEmailRequestBody("email.at.application.properties@mail.com", "Refatorando seu código", "Solicito a transmissão da Lightning Talk \"Refatorando seu código\", que ocorrerá no dia 25/03/2020 das 15:00 até 15:30");
        Object body = capture.getBody();
        assertNotNull(body);
        assertEquals(expectedRequestBody, body);
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

        assertTrue(String.valueOf(body).contains("email.at.application.properties@mail.com"));
    }
}