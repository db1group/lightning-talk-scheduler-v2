package com.db1group.ltscheduler;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static io.restassured.RestAssured.post;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class LightningTalkSchedulerApplicationTests {

	@MockBean
	private RestTemplate restTemplateMock;

	@Test
	void shouldScheduleANewLightningTalk() {
		mockScheduleTransmissionEmailSendResponse();

		post("schedule").then().statusCode(201);
	}

	@Test
	void shouldResultInABadRequestResponse_whenSomethingGoesWrong() {
		mockFailedTransmissionEmailSendResponse();

		post("schedule").then()
				.statusCode(400)
				.body(equalTo("It Failed"));
	}

	private void mockFailedTransmissionEmailSendResponse() {
		doReturn(ResponseEntity.badRequest().build())
				.when(restTemplateMock)
				.postForEntity(eq("https://graph.microsoft.com/v1.0/me/sendMail"), any(), any());
	}

	private void mockScheduleTransmissionEmailSendResponse() {
		doReturn(ResponseEntity.accepted().build())
				.when(restTemplateMock)
				.postForEntity(eq("https://graph.microsoft.com/v1.0/me/sendMail"), any(), any());
	}

}
