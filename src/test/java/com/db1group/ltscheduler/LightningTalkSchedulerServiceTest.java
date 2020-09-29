package com.db1group.ltscheduler;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
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

    private static final String TRANSMISSION_EMAIL_URL = "https://graph.microsoft.com/v1.0/me/sendMail";
    private static final String SCHEDULING_EMAIL_URL = "https://graph.microsoft.com/v1.0/me/calendar/events";

    @MockBean
    private RestTemplate restTemplateMock;

    @Autowired
    private LightningTalkSchedulerService lightningTalkSchedulerService;

    @Test
    public void shouldTestUrl_forPostEntity() {
        when(restTemplateMock.postForEntity(anyString(), any(HttpEntity.class), eq(String.class))).thenReturn(ResponseEntity.accepted().build());

        lightningTalkSchedulerService.schedule();

        verify(restTemplateMock).postForEntity(eq(TRANSMISSION_EMAIL_URL), any(), any());
    }

    @Test
    public void shouldTestBody_forPosEntity() {
        when(restTemplateMock.postForEntity(anyString(), any(HttpEntity.class), eq(String.class))).thenReturn(ResponseEntity.accepted().build());

        LocalDateTime startDate = LocalDateTime.of(2020, 03, 25, 15, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2020, 03, 25, 15, 30, 0);
        lightningTalkSchedulerService.schedule("Refatorando seu código", startDate, endDate);

        TransmissionEmailRequestBody expectedRequestBody = new TransmissionEmailRequestBody("email.at.application.properties@mail.com", "Refatorando seu código", "Solicito a transmissão da Lightning Talk \"Refatorando seu código\", que ocorrerá no dia 25/03/2020 das 15:00 até 15:30");

        assertEquals(expectedRequestBody, captureSentBody(TRANSMISSION_EMAIL_URL));
    }

    @Test
    public void shouldSendTransmissionEmailToTheConfiguredAddress() {
        when(restTemplateMock.postForEntity(anyString(), any(HttpEntity.class), eq(String.class))).thenReturn(ResponseEntity.accepted().build());

        LocalDateTime startDate = LocalDateTime.of(2020, 03, 25, 15, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2020, 03, 25, 15, 30, 0);
        lightningTalkSchedulerService.schedule("Refatorando seu código", startDate, endDate);

        Object body = captureSentBody(TRANSMISSION_EMAIL_URL);
        assertTrue(String.valueOf(body).contains("email.at.application.properties@mail.com"));
    }

    @Test
    public void whenSchedulingTheLightningTalkOnPeopleCalendar_ShouldSendToRightUrl() {
        when(restTemplateMock.postForEntity(anyString(), any(HttpEntity.class), eq(String.class))).thenReturn(ResponseEntity.accepted().build());

        lightningTalkSchedulerService.schedule();

        verify(restTemplateMock).postForEntity(eq(SCHEDULING_EMAIL_URL), any(), any());
    }

    @Test
    public void shouldTestBody_forSchedulingLightingTalkPeopleCalendarRequest() {
        when(restTemplateMock.postForEntity(anyString(), any(HttpEntity.class), eq(String.class))).thenReturn(ResponseEntity.accepted().build());

        LocalDateTime startDate = LocalDateTime.of(2020, 03, 25, 15, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2020, 03, 25, 15, 30, 0);
        lightningTalkSchedulerService.schedule("Refatorando seu código", startDate, endDate);

        SchedulingEmailRequestBody expectedRequestBody = new SchedulingEmailRequestBody("LT: Refatorando seu código", startDate, endDate, "Grande LightTalk sobre refatoração de código", "scheduling.email.at.application.properties@mail.com");

        assertEquals(expectedRequestBody, captureSentBody(SCHEDULING_EMAIL_URL));
    }

    private Object captureSentBody(String toUri) {
        ArgumentCaptor<HttpEntity> requestCaptor = ArgumentCaptor.forClass(HttpEntity.class);

        verify(restTemplateMock).postForEntity(eq(toUri), requestCaptor.capture(), any());
        HttpEntity capture = requestCaptor.getValue();

        return capture.getBody();
    }

    @Test
    public void shouldScheduleTheLightningTalkIntoTheConfiguredCalendar() {
        when(restTemplateMock.postForEntity(anyString(), any(HttpEntity.class), eq(String.class))).thenReturn(ResponseEntity.accepted().build());

        LocalDateTime startDate = LocalDateTime.of(2020, 03, 25, 15, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2020, 03, 25, 15, 30, 0);
        lightningTalkSchedulerService.schedule("Refatorando seu código", startDate, endDate);

        Object body = captureSentBody(SCHEDULING_EMAIL_URL);
        assertTrue(String.valueOf(body).contains("scheduling.email.at.application.properties@mail.com"));
    }
}