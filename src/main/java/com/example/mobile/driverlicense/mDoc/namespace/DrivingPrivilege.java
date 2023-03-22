package com.example.mobile.driverlicense.mDoc.namespace;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * D.2.1
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DrivingPrivilege {
    @JsonProperty("vehicle_category_code")
    private String vehicleCatagoryCode;

    @JsonProperty("issue_date")
    private LocalDateTime issueDate;

    @JsonProperty("expiry_date")
    private LocalDateTime expiryDate;
    private List<Code> codes = new ArrayList<Code>();
}
