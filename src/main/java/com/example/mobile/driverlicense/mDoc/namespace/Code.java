package com.example.mobile.driverlicense.mDoc.namespace;

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
public class Code {
    private String code;
    private String sign;
    private String value;
}
