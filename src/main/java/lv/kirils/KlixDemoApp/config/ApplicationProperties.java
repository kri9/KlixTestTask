package lv.kirils.KlixDemoApp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application")
public record ApplicationProperties(
    Integer statusPollingTime,
    BankUris fastUris,
    BankUris solidUris
) {

}
