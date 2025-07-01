package lv.kirils.KlixDemoApp.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.kirils.KlixDemoApp.dto.ApplicationRequest;
import lv.kirils.KlixDemoApp.dto.ApplicationResponse;
import lv.kirils.KlixDemoApp.service.ApplicationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MainController {

  private final ApplicationService applicationService;

  @PostMapping("/application")
  public ApplicationResponse application(@RequestBody ApplicationRequest request) {
    return applicationService.getApplicationOffers(request);
  }

  @ExceptionHandler(Exception.class)
  public ApplicationResponse handleException(Exception e) {
    log.error("Error while processing application", e);
    return new ApplicationResponse(null, List.of(e.getMessage()));
  }

}
