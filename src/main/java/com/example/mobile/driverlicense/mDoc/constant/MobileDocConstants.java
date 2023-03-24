package com.example.mobile.driverlicense.mDoc.constant;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MobileDocConstants {
    public static final String MDL_DOCTYPE = "org.iso.18013.5.1.mDL";
    public static final String MDL_NAMESPACE = "org.iso.18013.5.1";
    public static final String AAMVA_NAMESPACE = "org.iso.18013.5.1.aamva";

    // mDoc Data element access control
    public static final String PUBLIC = "public";
    public static final String PROTECTED = "protected";
    public static final String PRIVATE = "private";
    public static final String CONFIDENTIAL = "confidential";

    // namespace “org.iso.18013.5.1”
    public static final String FAMILY_NAME = "family_name";
    public static final String GIVEN_NAME = "given_name";
    public static final String BIRTH_DATE = "birth_date";
    public static final String ISSUE_DATE = "issue_date";
    public static final String EXPIRY_DATE = "expiry_date";
    public static final String ISSUING_COUNTRY = "issuing_country";
    public static final String ISSUING_AUTHORITY = "issuing_authority";
    public static final String DOCUMENT_NUMBER = "document_number";
    public static final String PORTRAIT = "portrait";
    public static final String DRIVING_PRIVILEGES = "driving_privileges";
    public static final String UN_DISTINGUISHING_SIGN = "un_distinguishing_sign";
    public static final String ADMINISTRATIVE_NUMBER = "administrative_number";
    public static final String SEX = "sex";
    public static final String HEIGHT = "height";
    public static final String WEIGHT = "weight";
    public static final String EYE_COLOUR = "eye_colour";
    public static final String HAIR_COLOUR = "hair_colour";
    public static final String BIRTH_PLACE = "birth_place";
    public static final String RESIDENT_ADDRESS = "resident_address";
    public static final String PORTRAIT_CAPTURE_DATE = "portrait_capture_date";
    public static final String AGE_IN_YEARS = "age_in_years";
    public static final String AGE_BIRTH_YEAR = "age_birth_year";
    public static final String AGE_OVER_NN = "age_over_";
    public static final String ISSUING_JURISDICTION = "issuing_jurisdiction";
    public static final String NATIONALITY = "nationality";
    public static final String RESIDENT_CITY = "resident_city";
    public static final String RESIDENT_STATE = "resident_state";
    public static final String RESIDNENT_POSTAL_CODE = "residnent_postal_code";
    public static final String RESIDENT_COUNTRY = "resident_country";
    public static final String BIOMETRIC_TEMPLATE_XX = "biometric_template_xx";
    public static final String FAMILY_NAME_NATIONAL_CHARACTER = "family_name_national_character";
    public static final String GIVEN_NAME_NATIONAL_CHARACTER = "given_name_national_character";
    public static final String SIGNATURE_USUAL_MARK = "signature_usual_mark";

    // 7.2.4
    public static final String VEHICLE_CATEGORY_CODE = "vehicle_category_code";
    public static final String CODES = "codes";
    public static final String CODE = "code";
    public static final String SIGN = "sign";
    public static final String VALUE = "value";

    // mDL holder’s sex using values as defined in ISO/IEC 5218.
    public static final List<Long> SEX_ID = Arrays.asList(0, 1, 2, 9)
            .stream()
            .mapToLong(i -> (long) i)
            .boxed()
            .collect(Collectors.toList());

    
}
