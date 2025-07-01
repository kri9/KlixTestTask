package lv.kirils.KlixDemoApp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

public record SolidAppRequest(
    @NotBlank
    @Pattern(regexp = "\\+[0-9]{11,15}")
    String phone,
    @NotBlank
    String email,
    @NotNull
    BigDecimal monthlyIncome,
    @NotNull
    BigDecimal monthlyExpenses,
    @NotNull
    MaritalStatus maritalStatus,
    @NotNull
    Boolean agreeToBeScored,
    @NotNull
    BigDecimal amount
) {

}
