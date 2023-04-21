package com.example.mobile.driverlicense.mdoc.mso;

import java.security.PublicKey;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Provides extra info for the mdoc authentication public key as part of the
 * <code>KeyInfo</code> portion of the <code>DeviceKeyInfo</code>.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeviceKeyInfo {

    /**
     * deviceKey contains the public part of the key pair used for mdoc authentication (see 9.1.3.4). The 
     * deviceKey element is encoded as an untagged COSE_Key element as specified in RFC 8152; further
     * requirements are defined in 9.1.5.2.
     */
    private PublicKey deviceKey;

    /**
     * KeyAuthorizations shall contain 
     * all the elements the key may sign or MAC. Authorizations can be given for a full namespace or per
     * data element.
     */
    private KeyAuthorizations keyAuthorizations;

    /**
     * KeyInfo may contain extra info about the key. Positive integers for KeyInfo labels are RFU. If applicationspecific
     * extensions are present, they shall use negative integers for the labels.
     */
    private Map<Long, byte[]> keyInfo;
}
