package com.example.mobile.driverlicense.mDoc.namespace;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;

import lombok.NonNull;

public class NamespaceData {

    private String mNamespace;
    private LinkedHashMap<String, EntryData> mEntries = new LinkedHashMap<>();

    public NamespaceData(@NonNull String namespace) {
        this.mNamespace = namespace;
    }

    String getNamespaceName() {
        return mNamespace;
    }

    Collection<String> getEntryNames() {
        return Collections.unmodifiableCollection(mEntries.keySet());
    }

    Collection<AccessControlProfileId> getAccessControlProfileIds(String name) {
        EntryData value = mEntries.get(name);
        if (value != null) {
            return value.mAccessControlProfileIds;
        }
        return null;
    }

    byte[] getEntryValue(String name) {
        EntryData value = mEntries.get(name);
        if (value != null) {
            return value.getMValue();
        }
        return null;
    }

    public LinkedHashMap<String, EntryData> getmEntries() {
        return mEntries;
    }

}
