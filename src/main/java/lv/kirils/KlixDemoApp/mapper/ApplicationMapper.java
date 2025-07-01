package lv.kirils.KlixDemoApp.mapper;

import lv.kirils.KlixDemoApp.dto.ApplicationRequest;
import lv.kirils.KlixDemoApp.dto.FastAppRequest;
import lv.kirils.KlixDemoApp.dto.SolidAppRequest;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ApplicationMapper {

  @Mapping(target = "monthlyIncomeAmount", source = "monthlyIncome")
  FastAppRequest toFastAppRequest(ApplicationRequest request);

  @Mapping(target = "phone", source = "phoneNumber")
  @Mapping(target = "maritalStatus", source = "status")
  SolidAppRequest toSolidAppRequest(ApplicationRequest request);

}
