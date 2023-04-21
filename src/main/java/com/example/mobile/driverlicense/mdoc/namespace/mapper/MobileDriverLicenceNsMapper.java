package com.example.mobile.driverlicense.mdoc.namespace.mapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mobile.driverlicense.driver.Code;
import com.example.mobile.driverlicense.driver.DriverDetails;
import com.example.mobile.driverlicense.driver.DrivingPrivilege;
import com.example.mobile.driverlicense.mdoc.config.MobileDocProperties;
import com.example.mobile.driverlicense.mdoc.contants.MobileDocConstants;
import com.example.mobile.driverlicense.mdoc.namespace.AccessControlProfileId;
import com.example.mobile.driverlicense.mdoc.utils.CborDataItemHelper;
import com.example.mobile.driverlicense.mdoc.utils.CborEncoderUtils;
import com.example.mobile.driverlicense.mdoc.utils.DurationUtils;
import com.example.mobile.driverlicense.mdoc.utils.StringParser;
import com.example.mobile.driverlicense.mdoc.validator.DriverDetailsValidator;

import co.nstant.in.cbor.CborBuilder;
import co.nstant.in.cbor.builder.ArrayBuilder;
import co.nstant.in.cbor.builder.MapBuilder;
import co.nstant.in.cbor.model.DataItem;
import lombok.NonNull;

@Service
public class MobileDriverLicenceNsMapper implements NamespaceDataMapperInf<DriverDetails> {

    @Autowired
    private MobileDocProperties mDocProperties;
    
    @Override
    public Map<String, DataItem> map(@NonNull DriverDetails driverDetails) {
        
        //!FIXME
        // if(!DriverDetailsValidator.isDriverDetailsValid(driverDetails)){
        //     // need to throw
        //     return null;
        // }

        Map<String, DataItem> map = new HashMap<String, DataItem>();

        // addAccessControlProfile(mData, createNoAuthProfile());
        // addAccessControlProfile(mData, protectedAuthProfile());

        AccessControlProfileId idNoAuth = new AccessControlProfileId(0);
        AccessControlProfileId idProtectedAuth = new AccessControlProfileId(1);

        Collection<AccessControlProfileId> idsNoAuth = new ArrayList<AccessControlProfileId>();
        idsNoAuth.add(idNoAuth);
        idsNoAuth.add(idProtectedAuth);

        Collection<AccessControlProfileId> idsProtectedAuth = new ArrayList<AccessControlProfileId>();
        idsProtectedAuth.add(idProtectedAuth);

        Optional<DriverDetails> details = Optional.ofNullable(driverDetails);

        //* 7.2.1 Table 5 Mandatory
        details.map(DriverDetails::getFamilyName).ifPresent(value -> map.put(MobileDocConstants.FAMILY_NAME, CborDataItemHelper.createDataItem(value)));
        details.map(DriverDetails::getGivenName).ifPresent(value -> map.put(MobileDocConstants.GIVEN_NAME, CborDataItemHelper.createDataItem(value)));
        details.map(DriverDetails::getBirthDate).ifPresent(value -> map.put(MobileDocConstants.BIRTH_DATE, CborEncoderUtils.cborBuildDateTime(StringParser.parseToLocalDateTime(value))));
        details.map(DriverDetails::getIssueDate).ifPresent(value -> map.put(MobileDocConstants.ISSUE_DATE, CborEncoderUtils.cborBuildDateTime(StringParser.parseToLocalDateTime(value))));
        details.map(DriverDetails::getExpiryDate).ifPresent(value -> map.put(MobileDocConstants.EXPIRY_DATE, CborEncoderUtils.cborBuildDateTime(StringParser.parseToLocalDateTime(value))));
        details.map(DriverDetails::getIssuingCountry).ifPresent(value -> map.put(MobileDocConstants.ISSUING_COUNTRY, CborDataItemHelper.createDataItem(value)));
        details.map(DriverDetails::getIssuingAuthority).ifPresent(value -> map.put(MobileDocConstants.ISSUING_AUTHORITY, CborDataItemHelper.createDataItem(value)));
        details.map(DriverDetails::getDocumentNumber).ifPresent(value -> map.put(MobileDocConstants.DOCUMENT_NUMBER, CborDataItemHelper.createDataItem(value)));
        details.map(DriverDetails::getPortrait).ifPresent(value -> map.put(MobileDocConstants.PORTRAIT, CborDataItemHelper.createDataItem(value.getBytes())));
        
        details.map(DriverDetails::getDrivingPrivileges).ifPresent(value -> {
            map.put(MobileDocConstants.DRIVING_PRIVILEGES, buildDrivingPrivileges(value));
        });
        
        details.map(DriverDetails::getUnDistinguishingSign).ifPresent(value -> map.put(MobileDocConstants.UN_DISTINGUISHING_SIGN, CborDataItemHelper.createDataItem(value)));

        
        
        //* 7.2.1 Table 5 Optional
        details.map(DriverDetails::getAdministrativeNumber).ifPresent(value -> map.put(MobileDocConstants.ADMINISTRATIVE_NUMBER, CborDataItemHelper.createDataItem(value)));

        details.map(DriverDetails::getSex).ifPresent(value -> {
            if(DriverDetailsValidator.isSexValid().apply(driverDetails).isValid()){
                map.put(MobileDocConstants.SEX, CborDataItemHelper.createDataItem(value));
            }
        });
        details.map(DriverDetails::getHeight).ifPresent(value -> {
            if(DriverDetailsValidator.isLongValid(value)) {
                map.put(MobileDocConstants.HEIGHT, CborDataItemHelper.createDataItem(value));
            }
        });
        details.map(DriverDetails::getWeight).ifPresent(value -> {
            if(DriverDetailsValidator.isLongValid(value)) {
                map.put(MobileDocConstants.WEIGHT, CborDataItemHelper.createDataItem(value));
            }
        });

        details.map(DriverDetails::getEyeColour).ifPresent(value -> map.put(MobileDocConstants.EYE_COLOUR, CborDataItemHelper.createDataItem(value)));
        details.map(DriverDetails::getHairColour).ifPresent(value -> map.put(MobileDocConstants.HAIR_COLOUR, CborDataItemHelper.createDataItem(value)));
        details.map(DriverDetails::getBirthPlace).ifPresent(value -> map.put(MobileDocConstants.BIRTH_PLACE, CborDataItemHelper.createDataItem(value)));
        details.map(DriverDetails::getResidentAddress).ifPresent(value -> map.put(MobileDocConstants.RESIDENT_ADDRESS, CborDataItemHelper.createDataItem(value)));
        details.map(DriverDetails::getPortraitCaptureDate).ifPresent(value -> map.put(MobileDocConstants.PORTRAIT_CAPTURE_DATE, CborDataItemHelper.createDataItem(value)));

        if(mDocProperties.isAgeInYears()) {
            details.map(DriverDetails::getBirthDate).ifPresent(value -> map.put(MobileDocConstants.AGE_IN_YEARS, CborDataItemHelper.createDataItem(DurationUtils.getAge(StringParser.parseToLocalDateTime(value)))));
        }

        if(mDocProperties.isAgeBirthYear()) {
            details.map(DriverDetails::getBirthDate).ifPresent(value -> CborDataItemHelper.createDataItem(StringParser.parseToLocalDateTime(value).getYear()));
        }

        //! FIXME see 7.2.5
        details.map(DriverDetails::getBirthDate).ifPresent(value -> createAgeOverNn(map, StringParser.parseToLocalDateTime(value)));

        details.map(DriverDetails::getIssuingJurisdiction).ifPresent(value -> map.put(MobileDocConstants.ISSUING_JURISDICTION, CborDataItemHelper.createDataItem(value)));
        details.map(DriverDetails::getNationality).ifPresent(value -> map.put(MobileDocConstants.NATIONALITY, CborDataItemHelper.createDataItem(value)));
        details.map(DriverDetails::getResidentCity).ifPresent(value -> map.put(MobileDocConstants.RESIDENT_CITY, CborDataItemHelper.createDataItem(value)));
        details.map(DriverDetails::getResidentState).ifPresent(value -> map.put(MobileDocConstants.RESIDENT_STATE, CborDataItemHelper.createDataItem(value)));
        details.map(DriverDetails::getResidnentPostalCode).ifPresent(value -> map.put(MobileDocConstants.RESIDNENT_POSTAL_CODE, CborDataItemHelper.createDataItem(value)));
        details.map(DriverDetails::getResidentCountry).ifPresent(value -> map.put(MobileDocConstants.RESIDENT_COUNTRY, CborDataItemHelper.createDataItem(value)));

        //! FIXME see 7.2.6 don't know what da hail is this.
        // details.map(DriverDetails::getBiometricTemplateXx).ifPresent(value -> map.put(MobileDocConstants.BIOMETRIC_TEMPLATE_XX, idsNoAuth, value.getBytes()));

        details.map(DriverDetails::getFamilyNameNationalCharacter).ifPresent(value -> map.put(MobileDocConstants.FAMILY_NAME_NATIONAL_CHARACTER, CborDataItemHelper.createDataItem(value)));
        details.map(DriverDetails::getGivenNameNationalCharacter).ifPresent(value -> map.put(MobileDocConstants.GIVEN_NAME_NATIONAL_CHARACTER, CborDataItemHelper.createDataItem(value)));
        details.map(DriverDetails::getSignatureUsualMark).ifPresent(value -> map.put(MobileDocConstants.SIGNATURE_USUAL_MARK, CborDataItemHelper.createDataItem(value)));
        //! double check data type and format ref 7.2.1 table 5 
        return map;
    }
    
    // ! FIXME see 7.2.4; example D.2.1 not sure what will be in here
    private DataItem buildDrivingPrivileges(List<DrivingPrivilege> drivingPrivileges) {
        ArrayBuilder<CborBuilder> arr = new CborBuilder().addArray();
        for (DrivingPrivilege data : drivingPrivileges) {
            MapBuilder<ArrayBuilder<CborBuilder>> map = arr.addMap();
            Optional.ofNullable(data).map(DrivingPrivilege::getVehicleCatagoryCode)
                    .ifPresent(value -> map.put(CborDataItemHelper.createDataItem(MobileDocConstants.VEHICLE_CATEGORY_CODE),
                            CborDataItemHelper.createDataItem(value.toString())));
            Optional.ofNullable(data).map(DrivingPrivilege::getIssueDate)
                    .ifPresent(value -> map.put(CborDataItemHelper.createDataItem(MobileDocConstants.ISSUE_DATE),
                            CborEncoderUtils.cborBuildDateTime(value)));
            Optional.ofNullable(data).map(DrivingPrivilege::getExpiryDate)
                    .ifPresent(value -> map.put(CborDataItemHelper.createDataItem(MobileDocConstants.EXPIRY_DATE),
                            CborEncoderUtils.cborBuildDateTime(value)));

            Optional.ofNullable(data).map(DrivingPrivilege::getCodes).ifPresent(codes -> {
                ArrayBuilder<CborBuilder> codesArr = new CborBuilder().addArray();
                for (Code code : codes) {
                    MapBuilder<ArrayBuilder<CborBuilder>> codeMap = codesArr.addMap();
                    Optional.ofNullable(code).map(Code::getCode)
                            .ifPresent(value -> codeMap.put(CborDataItemHelper.createDataItem(MobileDocConstants.CODE),
                                    CborDataItemHelper.createDataItem(value.toString())));
                    Optional.ofNullable(code).map(Code::getSign)
                            .ifPresent(value -> codeMap.put(CborDataItemHelper.createDataItem(MobileDocConstants.SIGN),
                                    CborDataItemHelper.createDataItem(value.toString())));
                    Optional.ofNullable(code).map(Code::getValue)
                            .ifPresent(value -> codeMap.put(CborDataItemHelper.createDataItem(MobileDocConstants.VALUE),
                                    CborDataItemHelper.createDataItem(value.toString())));
                    codeMap.end();
                }
                DataItem codesDataItem = codesArr.end().build().get(0);
                map.put(CborDataItemHelper.createDataItem(MobileDocConstants.CODES), codesDataItem);
            });

        }
        return arr.end().build().get(0);
    }

    // ! FIXME 7.2.5; example D.2.2
    private Map<String, DataItem> createAgeOverNn(Map<String, DataItem> map, LocalDateTime birthdate) {
        long ageInYears = DurationUtils.getAge(birthdate);
        for (Integer n : mDocProperties.getAgeOverNn()) {
            String key = MobileDocConstants.AGE_OVER_NN + String.valueOf(n);
            boolean value = ageInYears > n ? true : false;
            map.put(key, CborDataItemHelper.createDataItem(value));
        }
        return map;
    }

}

