package edu.cmu.java.development.application.resources.contactInfoImpl;

import edu.cmu.java.development.application.resources.ContactInfo;


/**
 * Created by urbano on 4/2/16.
 */
public class FacebookInfo implements ContactInfo {

    public FacebookInfo() {
    }

    public String getAttribute() {
        return "Facebook";
    }

    public String getExtraDescription() {
        return null;
    }

    public String getAtrributeValue() {
        return "www.facebook.com/teste";
    }

    public int getIconResId() {
        return 1;
    }
}
