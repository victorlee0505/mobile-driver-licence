package com.example.mobile.driverlicense.mDoc.namespace;

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
public class DrivingPrivileges {
    private List<DrivingPrivilege> drivingPrivileges = new ArrayList<DrivingPrivilege>();
}
