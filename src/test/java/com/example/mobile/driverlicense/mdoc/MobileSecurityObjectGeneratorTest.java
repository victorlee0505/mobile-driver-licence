package com.example.mobile.driverlicense.mdoc;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.mobile.driverlicense.driver.DriverDetails;
import com.example.mobile.driverlicense.mdoc.contants.CryptographicProtocols;
import com.example.mobile.driverlicense.mdoc.contants.MobileDocConstants;
import com.example.mobile.driverlicense.mdoc.mso.DeviceKeyInfo;
import com.example.mobile.driverlicense.mdoc.mso.MobileDataElement;
import com.example.mobile.driverlicense.mdoc.mso.MobileSecurityObject;
import com.example.mobile.driverlicense.mdoc.mso.ValidityInfo;
import com.example.mobile.driverlicense.mdoc.utils.BinaryUtils;
import com.example.mobile.driverlicense.mdoc.utils.CborDecoderUtils;
import com.example.mobile.driverlicense.mdoc.utils.CryptoKeyMaker;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class MobileSecurityObjectGeneratorTest {
    @Autowired
	private MobileDataElementBuilderService mDataBuilder;

	@Test
	public void testFullMSO() throws NoSuchAlgorithmException {

		CryptoKeyMaker keyMaker = new CryptoKeyMaker(CryptographicProtocols.ELLIPTIC_CURVE,
				CryptographicProtocols.SECP256R1);
		PublicKey deviceKey = keyMaker.getPublicKeyFromIntegers(
				new BigInteger(TestVectors.ISO_18013_5_ANNEX_D_STATIC_DEVICE_KEY_X, 16),
				new BigInteger(TestVectors.ISO_18013_5_ANNEX_D_STATIC_DEVICE_KEY_Y, 16));

		Map<Long, byte[]> keyInfo = new HashMap<>();
		keyInfo.put(10L, BinaryUtils.fromHex("C985"));

		DeviceKeyInfo deviceKeyInfo = DeviceKeyInfo.builder()
				.deviceKey(deviceKey)
				.keyInfo(keyInfo)
				.build();

		final LocalDateTime signedTimestamp = LocalDateTime.ofEpochSecond(1601559002000L, 0, ZoneOffset.UTC);
		final LocalDateTime validFromTimestamp = LocalDateTime.ofEpochSecond(1601559002000L, 0, ZoneOffset.UTC);
		final LocalDateTime validUntilTimestamp = LocalDateTime.ofEpochSecond(1633095002000L, 0, ZoneOffset.UTC);
		final LocalDateTime expectedTimestamp = LocalDateTime.ofEpochSecond(1611093002000L, 0, ZoneOffset.UTC);

		ValidityInfo validityInfo = ValidityInfo.builder()
				.signed(signedTimestamp)
				.validFrom(validFromTimestamp)
				.validUntil(validUntilTimestamp)
				.expectedUpdate(expectedTimestamp)
				.build();

		DriverDetails dd = createDriverDetails();

		MobileDataElement mData = mDataBuilder.createMobileDataElement(dd);

		MobileSecurityObject mso = MobileSecurityObject.builder()
				.deviceKeyInfo(deviceKeyInfo)
				.digestAlgorithm(CryptographicProtocols.DIGEST_SHA_256)
				.docType(MobileDocConstants.MDL_DOCTYPE)
				.mData(mData)
				.validityInfo(validityInfo)
				.verions(MobileDocConstants.MDL_DOC_VERSION)
				.build();

		// dummy data element in dummy namespace
		Map<String, List<String>> deviceKeyAuthorizedDataElements = new HashMap<>();
		deviceKeyAuthorizedDataElements.put("a", List.of("1", "2", "f"));
		deviceKeyAuthorizedDataElements.put("b", List.of("4", "5", "k"));

		byte[] encodedMSO = new MobileSecurityObjectGenerator(mso)
				.setDeviceKeyAuthorizedNameSpaces(List.of(MobileDocConstants.MDL_NAMESPACE))
				.setDeviceKeyAuthorizedDataElements(deviceKeyAuthorizedDataElements)
				.generate();

		log.info(CborDecoderUtils.cborPrettyPrint(encodedMSO));

		// MobileSecurityObjectParser.MobileSecurityObject mso = new MobileSecurityObjectParser()
		// 		.setMobileSecurityObject(encodedMSO).parse();

		// assertEquals("1.0", mso.getVersion());
		// assertEquals(digestAlgorithm, mso.getDigestAlgorithm());
		// assertEquals("org.iso.18013.5.1.mDL", mso.getDocType());

		// assertEquals(Set.of("org.iso.18013.5.1", "org.iso.18013.5.1.US"),
		// 		mso.getValueDigestNamespaces());
		// assertNull(mso.getDigestIDs("abc"));
		// checkISODigest(mso.getDigestIDs("org.iso.18013.5.1"), digestAlgorithm);
		// checkISOUSDigest(mso.getDigestIDs("org.iso.18013.5.1.US"), digestAlgorithm);

		// assertEquals(deviceKeyFromVector, mso.getDeviceKey());
		// assertEquals(List.of("abc", "bcd"), mso.getDeviceKeyAuthorizedNameSpaces());
		// assertEquals(deviceKeyAuthorizedDataElements, mso.getDeviceKeyAuthorizedDataElements());
		// assertEquals(keyInfo.keySet(), mso.getDeviceKeyInfo().keySet());
		// assertEquals(BinaryUtils.toHex(keyInfo.get(10L)), BinaryUtils.toHex(mso.getDeviceKeyInfo().get(10L)));

		// assertEquals(signedTimestamp, mso.getSigned());
		// assertEquals(validFromTimestamp, mso.getValidFrom());
		// assertEquals(validUntilTimestamp, mso.getValidUntil());
		// assertEquals(expectedTimestamp, mso.getExpectedUpdate());
	}

	public DriverDetails createDriverDetails() {
		DriverDetails dd = DriverDetails.builder()
		.familyName("Smith")
		.givenName("Paul")
		.build();
		return dd;
	}
}

