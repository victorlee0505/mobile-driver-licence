package com.example.mobile.driverlicense.mDoc.validator;

import java.util.Optional;

import com.example.mobile.driverlicense.driver.DriverDetails;
import com.example.mobile.driverlicense.mDoc.constant.MobileDocConstants;

public class DriverDetailsValidator {
    
    public static boolean isDriverDetailsValid(DriverDetails driverDetails) {
        return true;
    }

    public static boolean isSexValid(DriverDetails driverDetails) {
        Optional<Long> sex = Optional.ofNullable(driverDetails).map(DriverDetails::getSex);
        if(sex.isPresent()){
            return MobileDocConstants.SEX_ID.contains(sex.get());
        }
        return false;
    }

    public static boolean isLongValid(Long longValue) {
        return longValue > 0 ? true : false;
    }
}
