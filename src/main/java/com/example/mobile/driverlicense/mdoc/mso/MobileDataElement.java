package com.example.mobile.driverlicense.mdoc.mso;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.example.mobile.driverlicense.mdoc.namespace.AccessControlProfile;
import com.example.mobile.driverlicense.mdoc.namespace.NamespaceData;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * MobileDataElement is value to be digested of all data elements per namespace and become ValueDigests in MSO
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MobileDataElement {
    private ArrayList<AccessControlProfile> mProfiles = new ArrayList<>();
    private LinkedHashMap<String, NamespaceData> mNamespaces = new LinkedHashMap<>();
}
