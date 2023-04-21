package com.example.mobile.driverlicense.mdoc.mso;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 18013-5 9.1.2.4
 * <p>
 * structure for MSO
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MobileSecurityObject {
    /**
     * Version of the MobileSecurityObject
     */
    private String verions;

    /**
     * Message digest algorithm used
     */
    private String digestAlgorithm;

    /**
     * ValueDigests: to be digested of all data elements per namespace
     */
    private MobileDataElement mData;

    /**
     * DeviceKeyInfo
     */
    private DeviceKeyInfo deviceKeyInfo;

    /**
     * docType as used in Documents
     */
    private String docType;

    /**
     * ValidityInfo
     */
    private ValidityInfo validityInfo;

}
