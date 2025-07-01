package lv.kirils.KlixDemoApp.dto;

import java.math.BigDecimal;

public record ApplicationRequest(
    String phoneNumber,
    String email,
    BigDecimal monthlyIncome,
    BigDecimal monthlyCreditLiabilities,
    BigDecimal monthlyExpenses,
    Integer dependents,
    Boolean agreeToDataSharing,
    BigDecimal amount,
    MaritalStatus status,
    Boolean agreeToBeScored
) {
}
