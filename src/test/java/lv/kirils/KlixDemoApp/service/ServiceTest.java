package lv.kirils.KlixDemoApp.service;

import lv.kirils.KlixDemoApp.dto.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "application.status-polling-time=1"
)
public class ServiceTest {

    @Autowired
    private TestRestTemplate client;

    @MockitoBean
    private RestTemplate restTemplate;

    @Test
    void fullRequestReturnsMergedOffers() {
        var draft = new BankApplicationResponse("id", BankApplicationResponse.ApplicationStatus.DRAFT, null);
        var offer = new Offer(
                BigDecimal.valueOf(10),
                BigDecimal.valueOf(120),
                12,
                BigDecimal.valueOf(9.99),
                "2025-08-01"
        );
        var processed = new BankApplicationResponse("id", BankApplicationResponse.ApplicationStatus.PROCESSED, offer);
        ResponseEntity<BankApplicationResponse> entityOk = new ResponseEntity<>(processed, HttpStatus.OK);

        when(restTemplate.postForObject(contains("/FastBank/applications"), any(), eq(BankApplicationResponse.class)))
                .thenReturn(draft);
        when(restTemplate.getForEntity(contains("/FastBank/applications/id"), eq(BankApplicationResponse.class)))
                .thenReturn(entityOk);
        when(restTemplate.getForObject(contains("/FastBank/applications/id"), eq(BankApplicationResponse.class)))
                .thenReturn(processed);

        when(restTemplate.postForObject(contains("/SolidBank/applications"), any(), eq(BankApplicationResponse.class)))
                .thenReturn(draft);
        when(restTemplate.getForEntity(contains("/SolidBank/applications/id"), eq(BankApplicationResponse.class)))
                .thenReturn(entityOk);
        when(restTemplate.getForObject(contains("/SolidBank/applications/id"), eq(BankApplicationResponse.class)))
                .thenReturn(processed);

        Map<String, Object> body = Map.of(
                "phoneNumber", "+37126000000",
                "email", "john.doe@klix.app",
                "monthlyIncome", 150.0,
                "monthlyCreditLiabilities", 0.0,
                "monthlyExpenses", 10.0,
                "dependents", 0,
                "agreeToDataSharing", true,
                "amount", 150.0,
                "status", "MARRIED",
                "agreeToBeScored", true
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<ApplicationResponse> response = client.exchange(
                "/application",
                HttpMethod.POST,
                new HttpEntity<>(body, headers),
                ApplicationResponse.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        ApplicationResponse resp = response.getBody();
        assertThat(resp).isNotNull();
        assertThat(resp.offers()).hasSize(2);
        assertThat(resp.errors()).isEmpty();
    }
}