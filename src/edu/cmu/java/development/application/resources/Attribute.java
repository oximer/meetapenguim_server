package edu.cmu.java.development.application.resources;

/**
 * Created by urbano on 4/11/16.
 */
public class Attribute {

    private String name;
    private String iconPath;

    public String getName() {
        return name;
    }

    public Attribute() {
    }

    public Attribute(String name, String iconUrl) {
        this.name = name;
        this.iconPath = iconUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconUrl) {
        this.iconPath = iconUrl;
    }
}
