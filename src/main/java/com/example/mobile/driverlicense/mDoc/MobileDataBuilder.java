package com.example.mobile.driverlicense.mDoc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map.Entry;
import java.util.Optional;

import com.example.mobile.driverlicense.driver.DriverDetails;
import com.example.mobile.driverlicense.mDoc.constant.MobileDocConstants;
import com.example.mobile.driverlicense.mDoc.namespace.AccessControlProfile;
import com.example.mobile.driverlicense.mDoc.namespace.AccessControlProfileId;
import com.example.mobile.driverlicense.mDoc.namespace.EntryData;
import com.example.mobile.driverlicense.mDoc.namespace.NamespaceData;
import com.example.mobile.driverlicense.mDoc.utils.CborDecoderUtils;
import com.example.mobile.driverlicense.mDoc.utils.CborEncoderUtils;
import com.example.mobile.driverlicense.mDoc.utils.StringParser;
import com.example.mobile.driverlicense.mDoc.validator.DriverDetailsValidator;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class MobileDataBuilder {

    private MobileDataElement mData;
    private DriverDetails driverDetails;

    /**
     * Generate a brand new MobileDataElement
     */
    public MobileDataBuilder(DriverDetails driverDetails) {
        this.mData = new MobileDataElement();
        this.driverDetails = driverDetails;
    }

    /**
     * re-use existing MobileDataElement
     */
    public MobileDataBuilder(MobileDataElement mData, DriverDetails driverDetails) {
        this.mData = mData;
        this.driverDetails = driverDetails;
    }

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
    public MobileDataElement putEntry(@NonNull String namespace, @NonNull String name,
            @NonNull Collection<AccessControlProfileId> accessControlProfileIds,
            @NonNull byte[] value) {
        NamespaceData namespaceData = this.mData.getMNamespaces().get(namespace);
        if (namespaceData == null) {
            namespaceData = new NamespaceData(namespace);
            this.mData.getMNamespaces().put(namespace, namespaceData);
        }
        // TODO: validate/verify that value is proper CBOR.
        namespaceData.getmEntries().put(name, new EntryData(value, accessControlProfileIds));
        return this.mData;
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
    public MobileDataElement putEntryString(@NonNull String namespace, @NonNull String name,
            @NonNull Collection<AccessControlProfileId> accessControlProfileIds,
            @NonNull String value) {
        return putEntry(namespace, name, accessControlProfileIds,
                CborEncoderUtils.cborEncodeString(value));
    }

    public MobileDataElement putEntryString(@NonNull String namespace, @NonNull String name,
            @NonNull Collection<AccessControlProfileId> accessControlProfileIds, String accessControl,
            @NonNull String value) {
        return putEntry(namespace, name, accessControlProfileIds,
                CborEncoderUtils.cborEncodeString(accessControl, value));
    }

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
    public MobileDataElement putEntryBytestring(@NonNull String namespace, @NonNull String name,
            @NonNull Collection<AccessControlProfileId> accessControlProfileIds,
            @NonNull byte[] value) {
        return putEntry(namespace, name, accessControlProfileIds,
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
    public MobileDataElement putEntryInteger(@NonNull String namespace, @NonNull String name,
            @NonNull Collection<AccessControlProfileId> accessControlProfileIds,
            long value) {
        return putEntry(namespace, name, accessControlProfileIds,
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
    public MobileDataElement putEntryBoolean(@NonNull String namespace, @NonNull String name,
            @NonNull Collection<AccessControlProfileId> accessControlProfileIds,
            boolean value) {
        return putEntry(namespace, name, accessControlProfileIds,
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
    public MobileDataElement putEntryLocalDateTime(@NonNull String namespace, @NonNull String name,
            @NonNull Collection<AccessControlProfileId> accessControlProfileIds,
            @NonNull LocalDateTime value) {
        return putEntry(namespace, name, accessControlProfileIds,
                CborEncoderUtils.cborEncodeDateTime(value));
    }

    /**
     * Adds a new access control profile to the builder.
     *
     * @param profile The access control profile.
     * @return The builder.
     */
    public MobileDataElement addAccessControlProfile(@NonNull AccessControlProfile profile) {
        this.mData.getMProfiles().add(profile);
        return this.mData;
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

    private MobileDataElement mapper() {
        if(!DriverDetailsValidator.isDriverDetailsValid(this.driverDetails)){
            // need to throw
            return null;
        }

        addAccessControlProfile(createNoAuthProfile());
        addAccessControlProfile(protectedAuthProfile());

        AccessControlProfileId idNoAuth = new AccessControlProfileId(0);
        AccessControlProfileId idProtectedAuth = new AccessControlProfileId(1);

        Collection<AccessControlProfileId> idsNoAuth = new ArrayList<AccessControlProfileId>();
        idsNoAuth.add(idNoAuth);
        idsNoAuth.add(idProtectedAuth);

        Collection<AccessControlProfileId> idsProtectedAuth = new ArrayList<AccessControlProfileId>();
        idsProtectedAuth.add(idProtectedAuth);

        Optional<DriverDetails> details = Optional.ofNullable(this.driverDetails);

        //* 7.2.1 Table 5 Mandatory
        // details.map(DriverDetails::getFamilyName).ifPresent(value -> putEntryString(MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.FAMILY_NAME, idsNoAuth, value));
        details.map(DriverDetails::getFamilyName).ifPresent(value -> putEntryString(MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.FAMILY_NAME, idsProtectedAuth, MobileDocConstants.PROTECTED, value));

        details.map(DriverDetails::getGivenName).ifPresent(value -> putEntryString(MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.GIVEN_NAME, idsNoAuth, value));
        details.map(DriverDetails::getBirthDate).ifPresent(value -> putEntryLocalDateTime(MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.BIRTH_DATE, idsNoAuth, StringParser.parseToLocalDateTime(value)));
        details.map(DriverDetails::getIssueDate).ifPresent(value -> putEntryLocalDateTime(MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.ISSUE_DATE, idsNoAuth, StringParser.parseToLocalDateTime(value)));
        details.map(DriverDetails::getExpiryDate).ifPresent(value -> putEntryLocalDateTime(MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.EXPIRY_DATE, idsNoAuth, StringParser.parseToLocalDateTime(value)));
        details.map(DriverDetails::getIssuingCountry).ifPresent(value -> putEntryString(MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.ISSUING_COUNTRY, idsNoAuth, value));
        details.map(DriverDetails::getIssuingAuthority).ifPresent(value -> putEntryString(MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.ISSUING_AUTHORITY, idsNoAuth, value));
        details.map(DriverDetails::getDocumentNumber).ifPresent(value -> putEntryString(MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.DOCUMENT_NUMBER, idsNoAuth, value));
        details.map(DriverDetails::getPortrait).ifPresent(value -> putEntry(MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.PORTRAIT, idsNoAuth, value.getBytes()));
        //! FIXME see 7.2.4
        details.map(DriverDetails::getDrivingPrivileges).ifPresent(value -> putEntryString(MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.DRIVING_PRIVILEGES, idsNoAuth, value));
        details.map(DriverDetails::getUnDistinguishingSign).ifPresent(value -> putEntryString(MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.UN_DISTINGUISHING_SIGN, idsNoAuth, value));
        
        //* 7.2.1 Table 5 Optional
        details.map(DriverDetails::getAdministrativeNumber).ifPresent(value -> putEntryString(MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.ADMINISTRATIVE_NUMBER, idsNoAuth, value));
        details.map(DriverDetails::getSex).ifPresent(value -> putEntryInteger(MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.SEX, idsNoAuth, value));
        details.map(DriverDetails::getHeight).ifPresent(value -> putEntryInteger(MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.HEIGHT, idsNoAuth, value));
        details.map(DriverDetails::getWeight).ifPresent(value -> putEntryInteger(MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.WEIGHT, idsNoAuth, value));
        details.map(DriverDetails::getEyeColour).ifPresent(value -> putEntryString(MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.EYE_COLOUR, idsNoAuth, value));
        details.map(DriverDetails::getHairColour).ifPresent(value -> putEntryString(MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.HAIR_COLOUR, idsNoAuth, value));
        details.map(DriverDetails::getBirthPlace).ifPresent(value -> putEntryString(MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.BIRTH_PLACE, idsNoAuth, value));
        details.map(DriverDetails::getResidentAddress).ifPresent(value -> putEntryString(MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.RESIDENT_ADDRESS, idsNoAuth, value));
        details.map(DriverDetails::getPortraitCaptureDate).ifPresent(value -> putEntryString(MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.PORTRAIT_CAPTURE_DATE, idsNoAuth, value));
        details.map(DriverDetails::getAgeInYears).ifPresent(value -> putEntryInteger(MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.AGE_IN_YEARS, idsNoAuth, value));
        details.map(DriverDetails::getAgeBirthYear).ifPresent(value -> putEntryInteger(MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.AGE_BIRTH_YEAR, idsNoAuth, value));
        details.map(DriverDetails::isAgeOverNn).ifPresent(value -> putEntryBoolean(MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.AGE_OVER_NN, idsNoAuth, value));
        details.map(DriverDetails::getIssuingJurisdiction).ifPresent(value -> putEntryString(MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.ISSUING_JURISDICTION, idsNoAuth, value));
        details.map(DriverDetails::getNationality).ifPresent(value -> putEntryString(MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.NATIONALITY, idsNoAuth, value));
        details.map(DriverDetails::getResidentCity).ifPresent(value -> putEntryString(MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.RESIDENT_CITY, idsNoAuth, value));
        details.map(DriverDetails::getResidentState).ifPresent(value -> putEntryString(MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.RESIDENT_STATE, idsNoAuth, value));
        details.map(DriverDetails::getResidnentPostalCode).ifPresent(value -> putEntryString(MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.RESIDNENT_POSTAL_CODE, idsNoAuth, value));
        details.map(DriverDetails::getResidentCountry).ifPresent(value -> putEntryString(MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.RESIDENT_COUNTRY, idsNoAuth, value));
        details.map(DriverDetails::getBiometricTemplateXx).ifPresent(value -> putEntry(MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.BIOMETRIC_TEMPLATE_XX, idsNoAuth, value.getBytes()));
        details.map(DriverDetails::getFamilyNameNationalCharacter).ifPresent(value -> putEntryString(MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.FAMILY_NAME_NATIONAL_CHARACTER, idsNoAuth, value));
        details.map(DriverDetails::getGivenNameNationalCharacter).ifPresent(value -> putEntryString(MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.GIVEN_NAME_NATIONAL_CHARACTER, idsNoAuth, value));
        details.map(DriverDetails::getSignatureUsualMark).ifPresent(value -> putEntryString(MobileDocConstants.MDL_NAMESPACE, MobileDocConstants.SIGNATURE_USUAL_MARK, idsNoAuth, value));
        //! double check data type and format ref 7.2.1 table 5 
        return this.mData;
    }

    public static void main(String[] args) {

        DriverDetails driverDetails = DriverDetails.builder().familyName("John").build();
        MobileDataBuilder builder = new MobileDataBuilder(driverDetails);

        MobileDataElement mData = builder.mapper();

        for( Entry<String, NamespaceData> data : mData.getMNamespaces().entrySet()) {
            for( Entry<String, EntryData> d : data.getValue().getmEntries().entrySet()) {
                System.out.println(CborDecoderUtils.cborDecodeAll(d.getValue().getMValue()));
            }
        }

    }
}
