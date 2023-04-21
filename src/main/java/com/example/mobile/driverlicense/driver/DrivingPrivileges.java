package com.example.mobile.driverlicense.driver;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 7.2.4 Categories of vehicles/restrictions/conditions
 */
@Getter
@Setter
@NoArgsConstructor
public class DrivingPrivileges {
    private List<DrivingPrivilege> drivingPrivileges = new ArrayList<DrivingPrivilege>();
}
