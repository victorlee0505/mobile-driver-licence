package com.example.mobile.driverlicense.driver;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 7.2.4 Categories of vehicles/restrictions/conditions
 * <pre>
 * Code as per ISO/IEC 18013-2 Annex A
 * Sign as per ISO/IEC 18013-2 Annex A
 * Value as per ISO/IEC 18013-2 Annex A
 * </pre>
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Code {
    private String code;
    private String sign;
    private String value;
}
