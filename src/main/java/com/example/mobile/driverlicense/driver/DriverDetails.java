package com.example.mobile.driverlicense.driver;

import java.util.List;

import com.example.mobile.driverlicense.mDoc.namespace.DrivingPrivilege;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 7.2.1 Table 5
 */
@Getter
@Setter
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverDetails {

    /**
     * Last name, surname, or primary identifier, of the mDL holder.The value shall
     * only use latin1b charactersand shall have a maximum length of 150characters.
     */
    @JsonProperty("family_name")
    private String familyName;

    /**
     * First name(s), other name(s), or secondary
     * identifier, of the mDL holder.
     * The value shall only use latin1b characters
     * and shall have a maximum length of 150
     * characters.
     */
    @JsonProperty("given_name")
    private String givenName;

    /**
     * Day, month and year on which the mDL
     * holder was born. If unknown, approximate
     * date of birth
     */
    @JsonProperty("birth_date")
    private String birthDate;

    /**
     * Date when mDL was issued
     */
    @JsonProperty("issue_date")
    private String issueDate;

    /**
     * Date when mDL expires
     */
    @JsonProperty("expiry_date")
    private String expiryDate;

    /**
     * Alpha-2 country code, as defined in
     * ISO 3166-1, of the issuing authority’s country
     * or territory
     */
    @JsonProperty("issuing_country")
    private String issuingCountry;

    /**
     * Issuing authority name.
     * The value shall only use latin1b characters
     * and shall have a maximum length of 150
     * characters.
     */
    @JsonProperty("issuing_authority")
    private String issuingAuthority;

    /**
     * The number assigned or calculated by the
     * issuing authority.
     * The value shall only use latin1b characters
     * and shall have a maximum length of 150
     * characters.
     */
    @JsonProperty("document_number")
    private String documentNumber;

    /**
     * A reproduction of the mDL holder’s portrait.
     * See 7.2.2
     * byte str
     */
    @JsonProperty("portrait")
    private String portrait;

    /**
     * Driving privileges of the mDL holder. See
     * 7.2.4
     */
    @JsonProperty("driving_privileges")
    private List<DrivingPrivilege> drivingPrivileges;

    /**
     * Distinguishing sign of the issuing country
     * according to ISO/IEC 18013-1:2018, Annex
     * F.
     * If no applicable distinguishing sign is
     * available in ISO/IEC 18013-1, an IA may
     * use an empty identifier or another identifier
     * by which it is internationally recognized.
     * In this case the IA should ensure
     * there is no collision with other IA’s.
     */
    @JsonProperty("un_distinguishing_sign")
    private String unDistinguishingSign;

    /**
     * An audit control number assigned by the
     * issuing authority.
     * The value shall only use latin1b characters
     * and shall have a maximum length of 150
     * characters.
     */
    @JsonProperty("administrative_number")
    private String administrativeNumber;

    /**
     * 0 = Not known;
     * 1 = Male;
     * 2 = Female;
     * 9 = Not applicable.
     */
    @JsonProperty("sex")
    private long sex;

    /**
     * mDL holder’s height in centimetres
     */
    @JsonProperty("height")
    private long height;

    /**
     * mDL holder’s weight in kilograms
     */
    @JsonProperty("weight")
    private long weight;

    /**
     * mDL holder’s eye colour. The value shall
     * be one of the following: “black”, “blue”,
     * “brown”, “dichromatic”, “grey”, “green”,
     * “hazel”, “maroon”, “pink”, “unknown”.
     */
    @JsonProperty("eye_colour")
    private String eyeColour;

    /**
     * mDL holder’s hair colour. The value shall
     * be one of the following: “bald”, “black”,
     * “blond”, “brown”, “grey”, “red”, “auburn”,
     * “sandy”, “white”, “unknown”.
     */
    @JsonProperty("hair_colour")
    private String hairColour;

    /**
     * Country and municipality or state/province
     * where the mDL holder was born.
     * The value shall only use latin1b characters
     * and shall have a maximum length of 150
     * characters.
     */
    @JsonProperty("birth_place")
    private String birthPlace;

    /**
     * The place where the mDL holder resides
     * and/or may be contacted (street/house
     * number, municipality etc.).
     * The value shall only use latin1b characters
     * and shall have a maximum length of 150
     * characters.
     */
    @JsonProperty("resident_address")
    private String residentAddress;

    /**
     * Date when portrait was taken. tdate
     */
    @JsonProperty("portrait_capture_date")
    private String portraitCaptureDate;

    /**
     * The age of the mDL holder
     */
    // @JsonProperty("age_in_years")
    // private long ageInYears;

    /**
     * The year when the mDL holder was born
     */
    // @JsonProperty("age_birth_year")
    // private long ageBirthYear;

    /**
     * See 7.2.5
     */
    // @JsonProperty("age_over_nn")
    // private boolean ageOverNn;

    /**
     * Country subdivision code of the jurisdiction
     * that issued the mDL as defined in
     * ISO 3166-2:2020, Clause 8. The first part of
     * the code shall be the same as the value for
     * issuing_country.
     */
    @JsonProperty("issuing_jurisdiction")
    private String issuingJurisdiction;

    /**
     * Nationality of the mDL holder as a two
     * letter country code (alpha-2 code) defined
     * in ISO 3166-1
     */
    @JsonProperty("nationality")
    private String nationality;

    /**
     * The city where the mDL holder lives.
     * The value shall only use latin1b characters
     * and shall have a maximum length of 150
     * characters.
     */
    @JsonProperty("resident_city")
    private String residentCity;

    /**
     * The state/province/district where the
     * mDL holder lives.
     * The value shall only use latin1b characters
     * and shall have a maximum length of 150
     * characters.
     */
    @JsonProperty("resident_state")
    private String residentState;

    /**
     * The postal code of the mDL holder.
     * The value shall only use latin1b characters
     * and shall have a maximum length of 150
     * characters.
     */
    @JsonProperty("residnent_postal_code")
    private String residnentPostalCode;

    /**
     * The country where the mDL holder lives
     * as a two letter country code (alpha-2 code)
     * defined in ISO 3166-1.
     */
    @JsonProperty("resident_country")
    private String residentCountry;

    /**
     * See 7.2.6 byte str
     * <p>
     * EXAMPLE The “FACE” template corresponds to “biometric_template_face” and the “SIGNATURE/SIGN”
     * template corresponds to “biometric_template_signature_sign”.
     */
    @JsonProperty("biometric_template_xx")
    private String biometricTemplateXx;

    /**
     * The family name of the mDL holder using
     * full UTF-8 character set.
     */
    @JsonProperty("family_name_national_character")
    private String familyNameNationalCharacter;

    /**
     * The given name of the mDL holder using
     * full UTF-8 character set.
     */
    @JsonProperty("given_name_national_character")
    private String givenNameNationalCharacter;

    /**
     * Image of the signature or usual mark of the
     * mDL holder, see 7.2.7
     * byte str
     */
    @JsonProperty("signature_usual_mark")
    private String signatureUsualMark;
}
