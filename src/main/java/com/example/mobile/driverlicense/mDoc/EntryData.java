package com.example.mobile.driverlicense.mDoc;

import java.util.Collection;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
