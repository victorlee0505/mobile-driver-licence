package com.example.mobile.driverlicense.mDoc.mso;

import java.security.PublicKey;
import java.util.Map;

import com.example.mobile.driverlicense.mDoc.MobileDataElement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MobileSecurityObject {
    private String verions;
    private String digestAlgorithm;
    private MobileDataElement mData;
    private DeviceKeyInfo deviceKeyInfo;
    private String docType;
    private ValidityInfo validityInfo;

}
