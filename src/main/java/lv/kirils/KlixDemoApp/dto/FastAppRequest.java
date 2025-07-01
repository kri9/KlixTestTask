package lv.kirils.KlixDemoApp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

public record FastAppRequest(
    @NotBlank
    @Pattern(regexp = "\\+371[0-9]{8}")
    String phoneNumber,
    @NotBlank
    String email,
    @NotNull
    BigDecimal monthlyIncomeAmount,
    @NotNull
    BigDecimal monthlyCreditLiabilities,
    @NotNull
    Integer dependents,
    @NotNull
    Boolean agreeToDataSharing,
    @NotNull
    BigDecimal amount
) {

}
