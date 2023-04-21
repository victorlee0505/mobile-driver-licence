package com.example.mobile.driverlicense.mdoc.namespace;

import java.util.Collection;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * This is from package com.android.identity.PersonalizationData
 */
@Getter
@Setter
@Builder
public class EntryData {
    private byte[] mValue;
    Collection<AccessControlProfileId> mAccessControlProfileIds;

    public EntryData(byte[] value, Collection<AccessControlProfileId> accessControlProfileIds) {
        this.mValue = value;
        this.mAccessControlProfileIds = accessControlProfileIds;
    }
}
