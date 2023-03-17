package com.example.mobile.driverlicense.mDoc;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.example.mobile.driverlicense.mDoc.namespace.AccessControlProfile;
import com.example.mobile.driverlicense.mDoc.namespace.NamespaceData;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MobileDataElement {
    private ArrayList<AccessControlProfile> mProfiles = new ArrayList<>();
    private LinkedHashMap<String, NamespaceData> mNamespaces = new LinkedHashMap<>();
}
