package com.example.mobile.driverlicense.driver;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.mobile.driverlicense.mdoc.contants.VehicleCategoryCodes;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 7.2.4 Categories of vehicles/restrictions/conditions
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DrivingPrivilege {
    @JsonProperty("vehicle_category_code")
    private VehicleCategoryCodes vehicleCatagoryCode;

    @JsonProperty("issue_date")
    private LocalDateTime issueDate;

    @JsonProperty("expiry_date")
    private LocalDateTime expiryDate;
    private List<Code> codes = new ArrayList<Code>();
}
