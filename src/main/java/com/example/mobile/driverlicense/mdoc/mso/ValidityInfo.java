package com.example.mobile.driverlicense.mdoc.mso;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Sets the <code>ValidityInfo</code> structure which contains information related to the
 * validity of the MSO and its signature. This must be called before generating since this a
 * required component of the <code>MobileSecurityObject</code>.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ValidityInfo {
    /** The timestamp at which the MSO signature was created. */
    private LocalDateTime signed;

    /** The timestamp before which the MSO is not yet valid. This shall be equal or later than the signed element. */
    private LocalDateTime validFrom;

    /** The timestamp after which the MSO is no longer valid. This shall be later than the validFrom element. */
    private LocalDateTime validUntil;

    /** Optional: if provided, represents the timestamp at which the issuing authority infrastructure expects to re-sign the MSO, else, null */
    private LocalDateTime expectedUpdate;
}
