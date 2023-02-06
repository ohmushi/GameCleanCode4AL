package clean.code.domain.functional.model;

import lombok.*;

import java.util.Map;

@Getter
@Value
@Builder
@ToString
@EqualsAndHashCode
public class Speciality {
    int hpAtLevel1;
    int powerAtLevel1;
    int armorAtLevel1;
    SpecialityClass specialityClass;
    Map.Entry<SpecialityClass, Integer> bonus;
}
