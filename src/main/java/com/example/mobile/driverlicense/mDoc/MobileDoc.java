package com.example.mobile.driverlicense.mDoc;

import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.mobile.driverlicense.mDoc.namespace.AccessControlProfile;
import com.example.mobile.driverlicense.mDoc.namespace.NamespaceData;

public class MobileDoc {
    
    private String mDocType;
    private String mCredentialKeyAlias;
    private List<X509Certificate> mCertificateChain;
    private byte[] mProofOfProvisioningSha256;
    private List<AccessControlProfile> mAccessControlProfiles = new ArrayList<>();
    private Map<Integer, AccessControlProfile> mProfileIdToAcpMap = new HashMap<>();
    private List<NamespaceData> mNamespaceDatas = new ArrayList<>();
}
