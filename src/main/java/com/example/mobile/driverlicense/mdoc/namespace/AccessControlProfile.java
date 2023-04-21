package com.example.mobile.driverlicense.mdoc.namespace;

import java.security.cert.X509Certificate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This is from package com.android.identity.AccessControlProfile
 * <p>
 * establish known access control profile to the mDoc
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccessControlProfile {
    private AccessControlProfileId mAccessControlProfileId;
    private X509Certificate mReaderCertificate;
    private boolean mUserAuthenticationRequired;
    private long mUserAuthenticationTimeoutMillis;
}
