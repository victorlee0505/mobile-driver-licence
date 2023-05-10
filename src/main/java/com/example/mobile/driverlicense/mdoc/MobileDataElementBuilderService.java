package com.example.mobile.driverlicense.mdoc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mobile.driverlicense.driver.DriverDetails;
import com.example.mobile.driverlicense.mdoc.contants.MobileDocConstants;
import com.example.mobile.driverlicense.mdoc.mso.MobileDataElement;
import com.example.mobile.driverlicense.mdoc.namespace.AccessControlProfile;
import com.example.mobile.driverlicense.mdoc.namespace.AccessControlProfileId;
import com.example.mobile.driverlicense.mdoc.namespace.EntryData;
import com.example.mobile.driverlicense.mdoc.namespace.NamespaceData;
import com.example.mobile.driverlicense.mdoc.namespace.mapper.MobileDriverLicenceNsMapper;
import com.example.mobile.driverlicense.mdoc.utils.CborEncoderUtils;

import co.nstant.in.cbor.model.DataItem;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MobileDataElementBuilderService {
    
    @Autowired
    private MobileDriverLicenceNsMapper mDLNsMapper;

    /**
     * Adds a new entry to the builder.
     *
     * <p>
     * This is a low-level method which expects the data to be the bytes of a
     * <a href="https://tools.ietf.org/html/rfc7049">CBOR</a>
     * value. When possible, applications should use methods such as
     * {@link #putEntryString(String, String, Collection, String)} or
     * {@link #putEntryInteger(String, String, Collection, long)} which
     * accept normal Java data types.
     * </p>
     *
     * @param namespace               The namespace to use, e.g.
     *                                {@code org.iso.18013-5.2019}.
     * @param name                    The name of the entry, e.g. {@code height}.
     * @param accessControlProfileIds A set of access control profiles to use.
     * @param value                   The value to add, in CBOR encoding.
     * @return The builder.
     */
    public MobileDataElement putEntry(MobileDataElement mData, String namespace, String name,
            Collection<AccessControlProfileId> accessControlProfileIds,
            byte[] value) {
        NamespaceData namespaceData = mData.getMNamespaces().get(namespace);
        if (namespaceData == null) {
            namespaceData = new NamespaceData(namespace);
            mData.getMNamespaces().put(namespace, namespaceData);
        }
        // TODO: validate/verify that value is proper CBOR.
        namespaceData.getmEntries().put(name, new EntryData(value, accessControlProfileIds));
        return mData;
    }

    /**
     * Adds a new entry to the builder.
     *
     * <p>
     * This is a convenience method which encodes {@code value} as CBOR and adds the
     * resulting bytes using {@link #putEntry(String, String, Collection, byte[])}.
     * The
     * resulting CBOR will be pre-configured DataItem regardless of type.
     * </p>
     *
     * @param namespace               The namespace to use, e.g.
     *                                {@code org.iso.18013-5.2019}.
     * @param name                    The name of the entry, e.g. {@code height}.
     * @param accessControlProfileIds A set of access control profiles to use.
     * @param value                   The value to add.
     * @return The builder.
     */
    public MobileDataElement putEntryDataItem(MobileDataElement mData, String namespace, String name,
            Collection<AccessControlProfileId> accessControlProfileIds,
            DataItem value) {
        return putEntry(mData, namespace, name, accessControlProfileIds,
                CborEncoderUtils.cborEncode(value));
    }

    /**
     * Adds a new entry to the builder.
     *
     * <p>
     * This is a convenience method which encodes {@code value} as CBOR and adds the
     * resulting bytes using {@link #putEntry(String, String, Collection, byte[])}.
     * The
     * resulting CBOR will be major type 3 (text string).
     * </p>
     *
     * @param namespace               The namespace to use, e.g.
     *                                {@code org.iso.18013-5.2019}.
     * @param name                    The name of the entry, e.g. {@code height}.
     * @param accessControlProfileIds A set of access control profiles to use.
     * @param value                   The value to add.
     * @return The builder.
     */
    public MobileDataElement putEntryString(MobileDataElement mData, String namespace, String name,
            Collection<AccessControlProfileId> accessControlProfileIds,
            String value) {
        return putEntry(mData, namespace, name, accessControlProfileIds,
                CborEncoderUtils.cborEncodeString(value));
    }

    // public MobileDataElement putEntryString(String namespace, String name,
    //         Collection<AccessControlProfileId> accessControlProfileIds, String accessControl,
    //         String value) {
    //     return putEntry(namespace, name, accessControlProfileIds,
    //             CborEncoderUtils.cborEncodeString(accessControl, value));
    // }

    /**
     * Adds a new entry to the builder.
     *
     * <p>
     * This is a convenience method which encodes {@code value} as CBOR and adds the
     * resulting bytes using {@link #putEntry(String, String, Collection, byte[])}.
     * The
     * resulting CBOR will be major type 2 (bytestring).
     * </p>
     *
     * @param namespace               The namespace to use, e.g.
     *                                {@code org.iso.18013-5.2019}.
     * @param name                    The name of the entry, e.g. {@code height}.
     * @param accessControlProfileIds A set of access control profiles to use.
     * @param value                   The value to add.
     * @return The builder.
     */
    public MobileDataElement putEntryBytestring(MobileDataElement mData, String namespace, String name,
            Collection<AccessControlProfileId> accessControlProfileIds,
            byte[] value) {
        return putEntry(mData, namespace, name, accessControlProfileIds,
                CborEncoderUtils.cborEncodeBytestring(value));
    }

    /**
     * Adds a new entry to the builder.
     *
     * <p>
     * This is a convenience method which encodes {@code value} as CBOR and adds the
     * resulting bytes using {@link #putEntry(String, String, Collection, byte[])}.
     * The
     * resulting CBOR will be major type 0 (unsigned integer) if non-negative,
     * otherwise
     * major type 1 (negative integer).
     * </p>
     *
     * @param namespace               The namespace to use, e.g.
     *                                {@code org.iso.18013-5.2019}.
     * @param name                    The name of the entry, e.g. {@code height}.
     * @param accessControlProfileIds A set of access control profiles to use.
     * @param value                   The value to add.
     * @return The builder.
     */
    public MobileDataElement putEntryInteger(MobileDataElement mData, String namespace, String name,
            Collection<AccessControlProfileId> accessControlProfileIds,
            long value) {
        return putEntry(mData, namespace, name, accessControlProfileIds,
                CborEncoderUtils.cborEncodeNumber(value));
    }

    /**
     * Adds a new entry to the builder.
     *
     * <p>
     * This is a convenience method which encodes {@code value} as CBOR and adds the
     * resulting bytes using {@link #putEntry(String, String, Collection, byte[])}.
     * The
     * resulting CBOR will be major type 7 (simple value) with additional
     * information 20 (for
     * the value {@code false}) or 21 (for the value {@code true}).
     * </p>
     *
     * @param namespace               The namespace to use, e.g.
     *                                {@code org.iso.18013-5.2019}.
     * @param name                    The name of the entry, e.g. {@code height}.
     * @param accessControlProfileIds A set of access control profiles to use.
     * @param value                   The value to add.
     * @return The builder.
     */
    public MobileDataElement putEntryBoolean(MobileDataElement mData, String namespace, String name,
            Collection<AccessControlProfileId> accessControlProfileIds,
            boolean value) {
        return putEntry(mData, namespace, name, accessControlProfileIds,
                CborEncoderUtils.cborEncodeBoolean(value));
    }

    /**
     * Adds a new entry to the builder.
     *
     * <p>
     * This is a convenience method which encodes {@code value} as CBOR and adds the
     * resulting bytes using {@link #putEntry(String, String, Collection, byte[])}.
     * The
     * resulting CBOR will be a tagged string with tag 0 as per
     * <a href="https://tools.ietf.org/html/rfc7049#section-2.4.1">Section 2.4.1 of
     * RFC
     * 7049</a>. This means that the tagged string follows the standard format
     * described in
     * <a href="https://tools.ietf.org/html/rfc3339">RFC 3339</a>,
     * as refined by Section 3.3 of
     * <a href="https://tools.ietf.org/html/rfc4287">RFC 4287</a>.
     *
     * @param namespace               The namespace to use, e.g.
     *                                {@code org.iso.18013-5.2019}.
     * @param name                    The name of the entry, e.g. {@code height}.
     * @param accessControlProfileIds A set of access control profiles to use.
     * @param value                   The value to add.
     * @return The builder.
     */
    public MobileDataElement putEntryLocalDateTime(MobileDataElement mData, String namespace, String name,
            Collection<AccessControlProfileId> accessControlProfileIds,
            LocalDateTime value) {
        return putEntry(mData, namespace, name, accessControlProfileIds,
                CborEncoderUtils.cborEncodeDateTime(value));
    }

    /**
     * Adds a new access control profile to the builder.
     *
     * @param profile The access control profile.
     * @return The builder.
     */
    private MobileDataElement addAccessControlProfile(MobileDataElement mData, AccessControlProfile profile) {
        mData.getMProfiles().add(profile);
        return mData;
    }

    //! FIXME no cert
    public AccessControlProfile createNoAuthProfile() {
        return AccessControlProfile.builder()
                .mAccessControlProfileId(new AccessControlProfileId(0))
                .mUserAuthenticationRequired(false)
                .build();
    }

    //! FIXME no cert
    public AccessControlProfile protectedAuthProfile() {
        return AccessControlProfile.builder()
                .mAccessControlProfileId(new AccessControlProfileId(1))
                .mUserAuthenticationRequired(false)
                .build();
    }

    public MobileDataElement createMobileDataElement(DriverDetails driverDetails) {
        MobileDataElement mData = new MobileDataElement();
        return createMobileDataElement(mData, driverDetails);
    }

    public MobileDataElement createMobileDataElement(MobileDataElement mData, DriverDetails driverDetails) {
        // if(!DriverDetailsValidator.isDriverDetailsValid(driverDetails)){
        //     // need to throw
        //     return null;
        // }

        addAccessControlProfile(mData, createNoAuthProfile());
        addAccessControlProfile(mData, protectedAuthProfile());

        AccessControlProfileId idNoAuth = new AccessControlProfileId(0);
        AccessControlProfileId idProtectedAuth = new AccessControlProfileId(1);

        Collection<AccessControlProfileId> idsNoAuth = new ArrayList<AccessControlProfileId>();
        idsNoAuth.add(idNoAuth);
        idsNoAuth.add(idProtectedAuth);

        //TODO need to differentiate which element need auth
        Collection<AccessControlProfileId> idsProtectedAuth = new ArrayList<AccessControlProfileId>();
        idsProtectedAuth.add(idProtectedAuth);

        // MobileDocConstants.MDL_NAMESPACE = "org.iso.18013.5.1";
        Map<String, DataItem> mDlNsItems = mDLNsMapper.map(driverDetails);

        for (Entry<String, DataItem> item : mDlNsItems.entrySet()) {
            log.debug("Name: {} - data: {}", item.getKey(), item.getValue().toString());
        }

        for (Entry<String, DataItem> data : mDlNsItems.entrySet()) {
            putEntryDataItem(mData, MobileDocConstants.MDL_NAMESPACE, data.getKey(), idsNoAuth, data.getValue());
        }

        return mData;
    }

    // public static void main(String[] args) {

        // DriverDetails driverDetails = DriverDetails.builder().familyName("John").build();
        // MobileDataBuilder builder = new MobileDataBuilder();

        // MobileDataElement mData = builder.mapper();

        // for( Entry<String, NamespaceData> data : mData.getMNamespaces().entrySet()) {
        //     for( Entry<String, EntryData> d : data.getValue().getmEntries().entrySet()) {
        //         System.out.println(CborDecoderUtils.cborDecodeAll(d.getValue().getMValue()));
        //     }
        // }

    // }
}

