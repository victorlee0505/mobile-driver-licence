package com.example.mobile.driverlicense.mDoc.mso;

import java.security.PublicKey;
import java.util.Map;

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
public class DeviceKeyInfo {
    private PublicKey deviceKey;
    private KeyAuthorizations keyAuthorizations;
    private Map<Long, byte[]> keyInfo;
}
