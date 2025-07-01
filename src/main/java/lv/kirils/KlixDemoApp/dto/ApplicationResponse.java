package lv.kirils.KlixDemoApp.dto;

import java.util.List;
import java.util.stream.Stream;

public record ApplicationResponse(
    List<Offer> offers,
    List<String> errors
) {

  public ApplicationResponse {
    if (offers == null) {
      offers = List.of();
    }
    if (errors == null) {
      errors = List.of();
    }
  }

  public static ApplicationResponse merge(ApplicationResponse... responses) {
    return new ApplicationResponse(
        Stream.of(responses).flatMap(r -> r.offers.stream()).toList(),
        Stream.of(responses).flatMap(r -> r.errors.stream()).toList()
    );
  }

}
