package lv.kirils.KlixDemoApp.validator;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lv.kirils.KlixDemoApp.dto.FastAppRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ValidatorTest {

    private static Validator validator;

    @BeforeAll
    static void init() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void invalidPhoneNumberYieldsViolation() {
        FastAppRequest bad = new FastAppRequest(
                "12345",
                "a@b.com",
                BigDecimal.ONE,
                BigDecimal.ZERO,
                0,
                true,
                BigDecimal.ONE
        );

        Set<?> violations = validator.validate(bad);
        assertThat(violations).isNotEmpty();
    }
}
