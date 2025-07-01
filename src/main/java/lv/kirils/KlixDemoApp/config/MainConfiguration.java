package lv.kirils.KlixDemoApp.config;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lv.kirils.KlixDemoApp.dto.BankApplicationResponse;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Executor;

@EnableAsync
@Configuration
@EnableConfigurationProperties(ApplicationProperties.class)
public class MainConfiguration {

  @Bean
  public Validator validator() {
    return new LocalValidatorFactoryBean();
  }

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

  @Bean
  public Executor applicationExecutor() {
    return new ThreadPoolTaskExecutor();
  }


}
