package com.example.mobile.driverlicense.mDoc.validator;

import static com.example.mobile.driverlicense.mDoc.validator.ValidationResult.invalid;
import static com.example.mobile.driverlicense.mDoc.validator.ValidationResult.valid;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.example.mobile.driverlicense.driver.DriverDetails;
import com.example.mobile.driverlicense.mDoc.constant.MobileDocConstants;

public interface DriverDetailsValidator extends Function<DriverDetails, ValidationResult> {

    static final Predicate<String> STR_LENGTH_PREDICATE = str -> StringUtils.isNotBlank(str) && StringUtils.isAlpha(str) && StringUtils.length(str) <= 150;

    static final Predicate<DriverDetails> FAMILYNAME_PREDICATE = details -> {
        Optional<String> str = Optional.ofNullable(details).map(DriverDetails::getFamilyName);
        if (str.isPresent()) {
            return STR_LENGTH_PREDICATE.test(str.get());
        }
        return false;
    };

    static final Predicate<DriverDetails> SEX_CODE_PREDICATE = details -> {
        Optional<Long> sex = Optional.ofNullable(details).map(DriverDetails::getSex);
        if (sex.isPresent()) {
            return MobileDocConstants.SEX_ID.contains(sex.get());
        }
        return false;
    };

    default DriverDetailsValidator and(DriverDetailsValidator other) {
        return details -> {
            final ValidationResult result = this.apply(details);
            return result.isValid() ? other.apply(details) : result;
        };
    }

    default DriverDetailsValidator or(DriverDetailsValidator other) {
        return details -> {
            final ValidationResult result = this.apply(details);
            return result.isValid() ? other.apply(details) : result;
        };
    }

    static DriverDetailsValidator holds(Predicate<DriverDetails> p, String message) {
        return details -> p.test(details) ? valid() : invalid(message);
    }

    static DriverDetailsValidator all(DriverDetailsValidator... validations) {
        return user -> {
            String reasons = Arrays.stream(validations)
                    .map(v -> v.apply(user))
                    .filter(r -> !r.isValid())
                    .map(r -> r.getReason().get())
                    .collect(Collectors.joining("\n"));
            return reasons.isEmpty() ? valid() : invalid(reasons);
        };
    }

    //! WIP
    static ValidationResult isValidMandatoryData(DriverDetails driverDetails) {
        ValidationResult result = DriverDetailsValidator.isSexValid().and(DriverDetailsValidator.isValidFamilyName()).apply(driverDetails);
        return result;
    }

    static DriverDetailsValidator isValidFamilyName() {
        return holds(FAMILYNAME_PREDICATE, "Invalid Family Name: null or non-alpha or longer then 150.");
    }

    static boolean isValidGivenName() {
        return true;
    }

    static boolean isDriverDetailsValid() {
        return true;
    }

    static DriverDetailsValidator isSexValid() {
        return holds(SEX_CODE_PREDICATE, "Invalid SEX code");
    }

    static boolean isLongValid(Long longValue) {
        return longValue > 0 ? true : false;
    }
}
