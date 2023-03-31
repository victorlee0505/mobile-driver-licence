package com.example.mobile.driverlicense.mDoc.mso;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KeyAuthorizations {
    private List<String> nameSpaces;
    private Map<String, List<String>> dataElements;
}
