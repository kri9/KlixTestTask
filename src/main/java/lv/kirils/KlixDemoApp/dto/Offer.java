package lv.kirils.KlixDemoApp.dto;

import java.math.BigDecimal;

public record Offer(
    BigDecimal monthlyPaymentAmount,
    BigDecimal totalRepaymentAmount,
    Integer numberOfPayments,
    BigDecimal annualPercentageRate,
    String firstRepaymentDate
) {

}
