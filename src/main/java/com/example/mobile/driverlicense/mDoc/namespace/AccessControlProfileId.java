package com.example.mobile.driverlicense.mDoc.namespace;

/**
 * access control profile id (corresponding to {@link AccessControlProfile}) can be use on each data element
 * ex. profile id = 0 is general (like LCBO) and profile id = 1 is law enforcement.
 * LCBO only need your photo and if age > 19 while law enforcement need full access
 * then photo and age access are 0 & 1 while all other info in mDoc restricted to 1 only.
 * so if you hold a profile = 0 mDoc Reader, you can only access data element has profile id = 0.
 */
public class AccessControlProfileId {
    private int mId = 0;

    /**
     * Constructs a new object holding a numerical identifier.
     *
     * @param id the identifier.
     */
    public AccessControlProfileId(int id) {
        this.mId = id;
    }

    /**
     * Gets the numerical identifier wrapped by this object.
     *
     * @return the identifier.
     */
    public int getId() {
        return this.mId;
    }
}
