package com.example.mobile.driverlicense.mdoc.mso;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * As specified in 9.1.3.4, an mdoc can use a DeviceKey to calculate a signature or MAC over data 
 * elements as part of mdoc authentication. Within DeviceKeyInfo, KeyAuthorizations shall contain
 * all the elements the key may sign or MAC. 
 * <p>
 * Authorizations can be given for a full namespace or per
 * data element. If authorization is given for a full namespace (by including the namespace in the
 * AuthorizedNameSpaces array), that namespace shall not be included in the AuthorizedDataElements
 * map. If the KeyAuthorizations map is present, it shall not be empty.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KeyAuthorizations {

    /** A list of namespaces which should be given authorization for all of its data element. */
    private List<String> nameSpaces;

    /** A list of namespaces which should be given authorization for some of its data element. */
    private Map<String, List<String>> dataElements;
}
