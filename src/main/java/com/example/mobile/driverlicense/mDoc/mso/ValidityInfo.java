package com.example.mobile.driverlicense.mDoc.mso;

import java.time.LocalDateTime;

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
public class ValidityInfo {
    private LocalDateTime signed;
    private LocalDateTime validFrom;
    private LocalDateTime validUntil;
    private LocalDateTime expectedUpdate;
}
