package com.example.mobile.driverlicense.mDoc.namespace;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DrivingPrivilege {
    private String vehicleCatagoryCode;
    private LocalDateTime issueDate;
    private LocalDateTime expiryDate;
    private List<Code> codes = new ArrayList<Code>();
}
