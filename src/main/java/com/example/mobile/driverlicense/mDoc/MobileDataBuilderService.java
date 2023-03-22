package com.example.mobile.driverlicense.mDoc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map.Entry;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mobile.driverlicense.driver.DriverDetails;
import com.example.mobile.driverlicense.mDoc.constant.MobileDocConstants;
import com.example.mobile.driverlicense.mDoc.namespace.AccessControlProfile;
import com.example.mobile.driverlicense.mDoc.namespace.AccessControlProfileId;
import com.example.mobile.driverlicense.mDoc.namespace.DrivingPrivilege;
import com.example.mobile.driverlicense.mDoc.namespace.DrivingPrivileges;
import com.example.mobile.driverlicense.mDoc.namespace.EntryData;
import com.example.mobile.driverlicense.mDoc.namespace.NamespaceData;
import com.example.mobile.driverlicense.mDoc.utils.CborDecoderUtils;
import com.example.mobile.driverlicense.mDoc.utils.CborEncoderUtils;
import com.example.mobile.driverlicense.mDoc.utils.StringParser;
import com.example.mobile.driverlicense.mDoc.validator.DriverDetailsValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.NonNull;

@Service
public class MobileDataBuilderService {

    @Autowired
    private ObjectMapper jacksonMapper;

    // private MobileDataElement mData;
    // private DriverDetails driverDetails;

    // /**
    //  * Generate a brand new MobileDataElement
    //  */
    // public MobileDataBuilder(DriverDetails driverDetails) {
    //     this.mData = new MobileDataElement();
    //     this.driverDetails = driverDetails;
    // }

    // /**
    //  * re-use existing MobileDataElement
    //  */
    // public MobileDataBuilder(MobileDataElement mData, DriverDetails driverDetails) {
    //     this.mData = mData;
    //     this.driverDetails = driverDetails;
    // }

    /**
     * generate a new MobileDataElement from DriverDetails
     * @param driverDetails
     * @return MobileDataElement
     */
    public MobileDataElement buildFromDriverDetails() {
        
        return null;
    }
    
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
    public MobileDataElement putEntry(@NonNull MobileDataElement mData, @NonNull String namespace, @NonNull String name,
            @NonNull Collection<AccessControlProfileId> accessControlProfileIds,
            @NonNull byte[] value) {
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
    public MobileDataElement putEntryString(@NonNull MobileDataElement mData, @NonNull String namespace, @NonNull String name,
            @NonNull Collection<AccessControlProfileId> accessControlProfileIds,
            @NonNull String value) {
        return putEntry(mData, namespace, name, accessControlProfileIds,
                CborEncoderUtils.cborEncodeString(value));
    }

    // public MobileDataElement putEntryString(@NonNull String namespace, @NonNull String name,
    //         @NonNull Collection<AccessControlProfileId> accessControlProfileIds, String accessControl,
    //         @NonNull String value) {
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
    public MobileDataElement putEntryBytestring(@NonNull MobileDataElement mData, @NonNull String namespace, @NonNull String name,
            @NonNull Collection<AccessControlProfileId> accessControlProfileIds,
            @NonNull byte[] value) {
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
    public MobileDataElement putEntryInteger(@NonNull MobileDataElement mData, @NonNull String namespace, @NonNull String name,
            @NonNull Collection<AccessControlProfileId> accessControlProfileIds,
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
    public MobileDataElement putEntryBoolean(@NonNull MobileDataElement mData, @NonNull String namespace, @NonNull String name,
            @NonNull Collection<AccessControlProfileId> accessControlProfileIds,
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
    public MobileDataElement putEntryLocalDateTime(@NonNull MobileDataElement mData, @NonNull String namespace, @NonNull String name,
            @NonNull Collection<AccessControlProfileId> accessControlProfileIds,
            @NonNull LocalDateTime value) {
        return putEntry(mData, namespace, name, accessControlProfileIds,
                CborEncoderUtils.cborEncodeDateTime(value));
    }

    /**
     * Adds a new access control profile to the builder.
     *
     * @param profile The access control profile.
     * @return The builder.
     */
    private MobileDataElement addAccessControlProfile(@NonNull MobileDataElement mData, @NonNull AccessControlProfile profile) {
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

    private DrivingPrivilege buildDrivingPrivilege() {
        DrivingPrivilege drivingPrivilege = new DrivingPrivilege();
        return drivingPrivilege;
    }

    private DrivingPrivileges buildDrivingPrivileges(DriverDetails driverDetails) {
        if(!DriverDetailsValidator.isDriverDetailsValid(driverDetails)){
            // need to throw
            return null;
        }
        DrivingPrivileges drivingPrivileges = new DrivingPrivileges();

        //! FIXME dummy obj
        drivingPrivileges.getDrivingPrivileges().add(buildDrivingPrivilege());

        return drivingPrivileges;
    }

    public MobileDataElement mapper(@NonNull MobileDataElement mData, @NonNull DriverDetails driverDetails) {
        if(!DriverDetailsValidator.isDriverDetailsValid(driverDetails)){
            // need to throw
            return null;
        }

        addAccessControlProfile(mData, createNoAuthProfile());
        addAccessControlProfile(mData, protectedAuthProfile());

        AccessControlProfileId idNoAuth = new AccessControlProfileId(0);
        AccessControlProfileId idProtectedAuth = new AccessControlProfileId(1);

        Collection<AccessControlProfileId> idsNoAuth = new ArrayList<AccessControlProfileId>();
        idsNoAuth.add(idNoAuth);
        idsNoAuth.add(idProtectedAuth);

        Collection<AccessControlProfileId> idsProtectedAuth = new ArrayList<AccessControlProfileId>();
        idsProtectedAuth.add(idProtectedAuth);

        Optional<DriverDetails> details = Optional.ofNullable(driverDetails);

        //* 7.2.1 Table 5 Mandatory
        details.map(DriverDetails::getFamilyName).ifPresent(value -> putEntryString(mData, MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.FAMILY_NAME, idsNoAuth, value));
        // details.map(DriverDetails::getFamilyName).ifPresent(value -> putEntryString(mData, MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.FAMILY_NAME, idsProtectedAuth, MobileDocConstants.PROTECTED, value));

        details.map(DriverDetails::getGivenName).ifPresent(value -> putEntryString(mData, MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.GIVEN_NAME, idsNoAuth, value));
        details.map(DriverDetails::getBirthDate).ifPresent(value -> putEntryLocalDateTime(mData, MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.BIRTH_DATE, idsNoAuth, StringParser.parseToLocalDateTime(value)));
        details.map(DriverDetails::getIssueDate).ifPresent(value -> putEntryLocalDateTime(mData, MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.ISSUE_DATE, idsNoAuth, StringParser.parseToLocalDateTime(value)));
        details.map(DriverDetails::getExpiryDate).ifPresent(value -> putEntryLocalDateTime(mData, MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.EXPIRY_DATE, idsNoAuth, StringParser.parseToLocalDateTime(value)));
        details.map(DriverDetails::getIssuingCountry).ifPresent(value -> putEntryString(mData, MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.ISSUING_COUNTRY, idsNoAuth, value));
        details.map(DriverDetails::getIssuingAuthority).ifPresent(value -> putEntryString(mData, MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.ISSUING_AUTHORITY, idsNoAuth, value));
        details.map(DriverDetails::getDocumentNumber).ifPresent(value -> putEntryString(mData, MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.DOCUMENT_NUMBER, idsNoAuth, value));
        details.map(DriverDetails::getPortrait).ifPresent(value -> putEntry(mData, MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.PORTRAIT, idsNoAuth, value.getBytes()));
        //! FIXME see 7.2.4
        details.map(DriverDetails::getDrivingPrivileges).ifPresent(value -> {
            DrivingPrivileges dps = buildDrivingPrivileges(details.get());
            String stringValue;
            try {
                stringValue = jacksonMapper.writeValueAsString(dps.getDrivingPrivileges());
                putEntryString(mData, MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.DRIVING_PRIVILEGES, idsNoAuth, stringValue);
            } catch (JsonProcessingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
        details.map(DriverDetails::getUnDistinguishingSign).ifPresent(value -> putEntryString(mData, MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.UN_DISTINGUISHING_SIGN, idsNoAuth, value));
        
        //* 7.2.1 Table 5 Optional
        details.map(DriverDetails::getAdministrativeNumber).ifPresent(value -> putEntryString(mData, MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.ADMINISTRATIVE_NUMBER, idsNoAuth, value));
        details.map(DriverDetails::getSex).ifPresent(value -> putEntryInteger(mData, MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.SEX, idsNoAuth, value));
        details.map(DriverDetails::getHeight).ifPresent(value -> putEntryInteger(mData, MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.HEIGHT, idsNoAuth, value));
        details.map(DriverDetails::getWeight).ifPresent(value -> putEntryInteger(mData, MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.WEIGHT, idsNoAuth, value));
        details.map(DriverDetails::getEyeColour).ifPresent(value -> putEntryString(mData, MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.EYE_COLOUR, idsNoAuth, value));
        details.map(DriverDetails::getHairColour).ifPresent(value -> putEntryString(mData, MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.HAIR_COLOUR, idsNoAuth, value));
        details.map(DriverDetails::getBirthPlace).ifPresent(value -> putEntryString(mData, MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.BIRTH_PLACE, idsNoAuth, value));
        details.map(DriverDetails::getResidentAddress).ifPresent(value -> putEntryString(mData, MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.RESIDENT_ADDRESS, idsNoAuth, value));
        details.map(DriverDetails::getPortraitCaptureDate).ifPresent(value -> putEntryString(mData, MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.PORTRAIT_CAPTURE_DATE, idsNoAuth, value));
        details.map(DriverDetails::getAgeInYears).ifPresent(value -> putEntryInteger(mData, MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.AGE_IN_YEARS, idsNoAuth, value));
        details.map(DriverDetails::getAgeBirthYear).ifPresent(value -> putEntryInteger(mData, MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.AGE_BIRTH_YEAR, idsNoAuth, value));
        details.map(DriverDetails::isAgeOverNn).ifPresent(value -> putEntryBoolean(mData, MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.AGE_OVER_NN, idsNoAuth, value));
        details.map(DriverDetails::getIssuingJurisdiction).ifPresent(value -> putEntryString(mData, MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.ISSUING_JURISDICTION, idsNoAuth, value));
        details.map(DriverDetails::getNationality).ifPresent(value -> putEntryString(mData, MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.NATIONALITY, idsNoAuth, value));
        details.map(DriverDetails::getResidentCity).ifPresent(value -> putEntryString(mData, MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.RESIDENT_CITY, idsNoAuth, value));
        details.map(DriverDetails::getResidentState).ifPresent(value -> putEntryString(mData, MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.RESIDENT_STATE, idsNoAuth, value));
        details.map(DriverDetails::getResidnentPostalCode).ifPresent(value -> putEntryString(mData, MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.RESIDNENT_POSTAL_CODE, idsNoAuth, value));
        details.map(DriverDetails::getResidentCountry).ifPresent(value -> putEntryString(mData, MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.RESIDENT_COUNTRY, idsNoAuth, value));
        details.map(DriverDetails::getBiometricTemplateXx).ifPresent(value -> putEntry(mData, MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.BIOMETRIC_TEMPLATE_XX, idsNoAuth, value.getBytes()));
        details.map(DriverDetails::getFamilyNameNationalCharacter).ifPresent(value -> putEntryString(mData, MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.FAMILY_NAME_NATIONAL_CHARACTER, idsNoAuth, value));
        details.map(DriverDetails::getGivenNameNationalCharacter).ifPresent(value -> putEntryString(mData, MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.GIVEN_NAME_NATIONAL_CHARACTER, idsNoAuth, value));
        details.map(DriverDetails::getSignatureUsualMark).ifPresent(value -> putEntryString(mData, MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.SIGNATURE_USUAL_MARK, idsNoAuth, value));
        //! double check data type and format ref 7.2.1 table 5 
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
