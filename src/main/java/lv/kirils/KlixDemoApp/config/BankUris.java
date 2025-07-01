package lv.kirils.KlixDemoApp.config;

import lombok.Value;

@Value
public class BankUris {

  String submitUri;
  String applicationUri;

  public String getApplicationUri(String id) {
    return String.format(applicationUri, id);
  }

}
