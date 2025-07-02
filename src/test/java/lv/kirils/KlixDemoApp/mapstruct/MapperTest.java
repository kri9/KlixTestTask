package lv.kirils.KlixDemoApp.mapstruct;

import lv.kirils.KlixDemoApp.dto.*;
import lv.kirils.KlixDemoApp.mapper.ApplicationMapper;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class MapperTest {

    private final ApplicationMapper mp = Mappers.getMapper(ApplicationMapper.class);

    @Test
    void mapsPhoneAndIncomeToFastAppRequest() {
        ApplicationRequest in = new ApplicationRequest(
                "+37126000000", "x@y.z",
                BigDecimal.valueOf(500), BigDecimal.ZERO,
                BigDecimal.ZERO, 0, true,
                BigDecimal.valueOf(100),
                MaritalStatus.SINGLE, true
        );
        FastAppRequest fast = mp.toFastAppRequest(in);
        assertThat(fast.phoneNumber()).isEqualTo(in.phoneNumber());
        assertThat(fast.monthlyIncomeAmount()).isEqualByComparingTo(in.monthlyIncome());
    }
}