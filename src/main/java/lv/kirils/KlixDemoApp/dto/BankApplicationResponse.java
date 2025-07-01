package lv.kirils.KlixDemoApp.dto;

public record BankApplicationResponse(
    String id,
    ApplicationStatus status,
    Offer offer
) {

  public enum ApplicationStatus {
    DRAFT,
    PROCESSED
  }

}
