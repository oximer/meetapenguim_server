package edu.cmu.java.development.application.resources.contactInfoImpl;

import edu.cmu.java.development.application.resources.ContactInfo;

/**
 * Created by urbano on 4/2/16.
 */
public class LocationInfo implements ContactInfo {


    public LocationInfo() {

    }

    public String getAttribute() {
        return "Location";
    }

    public String getExtraDescription() {
        return null;
    }

    public String getAtrributeValue() {
        return "Cupertino - CA";
    }

    public int getIconResId() {
        return 1;
    }
}
