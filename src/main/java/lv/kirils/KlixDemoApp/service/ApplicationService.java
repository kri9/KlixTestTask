package lv.kirils.KlixDemoApp.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.kirils.KlixDemoApp.config.ApplicationProperties;
import lv.kirils.KlixDemoApp.config.BankUris;
import lv.kirils.KlixDemoApp.dto.ApplicationRequest;
import lv.kirils.KlixDemoApp.dto.ApplicationResponse;
import lv.kirils.KlixDemoApp.dto.BankApplicationResponse;
import lv.kirils.KlixDemoApp.mapper.ApplicationMapper;
import org.awaitility.Awaitility;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplicationService {

  private final Validator validator;
  private final RestTemplate restTemplate;
  private final ApplicationMapper applicationMapper;
  private final ApplicationProperties applicationProperties;

  public ApplicationResponse getApplicationOffers(ApplicationRequest request) {
    var fastReq = applicationMapper.toFastAppRequest(request);
    var solidReq = applicationMapper.toSolidAppRequest(request);
    CompletableFuture<ApplicationResponse> fastFuture = requestOfferAsync(applicationProperties.fastUris(), fastReq);
    CompletableFuture<ApplicationResponse> solidFuture = requestOfferAsync(applicationProperties.solidUris(), solidReq);
    return fastFuture
        .thenCombine(solidFuture, ApplicationResponse::merge)
        .join();
  }

  @Async
  public CompletableFuture<ApplicationResponse> requestOfferAsync(BankUris bankUris, Object body) {
    return CompletableFuture.supplyAsync(() -> requestOffer(bankUris, body));
  }

  private ApplicationResponse requestOffer(BankUris uris, Object requestBody) {
    Set<ConstraintViolation<Object>> errors = validator.validate(requestBody);
    if (!errors.isEmpty()) {
      return new ApplicationResponse(null, errors.stream().map(ConstraintViolation::getMessage).toList());
    }
    try {
      return executeRestExchanges(uris, requestBody);
    } catch (Exception e) {
      log.error("Error while requesting offer from {}", uris.getSubmitUri(), e);
      return new ApplicationResponse(Collections.emptyList(), List.of(e.getMessage()));
    }
  }

  private ApplicationResponse executeRestExchanges(BankUris uris, Object requestBody) {
    BankApplicationResponse resp = Optional.ofNullable(
            restTemplate.postForObject(uris.getSubmitUri(), requestBody, BankApplicationResponse.class))
        .map(BankApplicationResponse::id)
        .flatMap(id -> waitAndGetOffer(uris, id))
        .orElse(null);
    if (resp == null || resp.offer() == null) {
      return new ApplicationResponse(null, List.of("No offer found from " + uris.getSubmitUri()));
    }
    return new ApplicationResponse(List.of(resp.offer()), Collections.emptyList());
  }

  private Optional<BankApplicationResponse> waitAndGetOffer(BankUris uris, String id) {
    try {
      Awaitility.await()
          .atMost(Duration.ofSeconds(applicationProperties.statusPollingTime()))
          .pollInterval(Duration.ofSeconds(5))
          .until(() -> statusIsProcessed(uris.getApplicationUri(id)));
      return Optional.ofNullable(restTemplate.getForObject(uris.getApplicationUri(id), BankApplicationResponse.class));
    } catch (Exception e) {
      return Optional.empty();
    }
  }

  private boolean statusIsProcessed(String url) {
    BankApplicationResponse resp = restTemplate.getForEntity(url, BankApplicationResponse.class).getBody();
    log.info("Received response from {}: {}", url, resp);
    return Optional.ofNullable(resp)
        .map(BankApplicationResponse::status)
        .map(BankApplicationResponse.ApplicationStatus.PROCESSED::equals)
        .orElse(false);
  }

}
