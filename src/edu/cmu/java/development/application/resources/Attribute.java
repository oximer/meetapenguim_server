package edu.cmu.java.development.application.resources;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Attribute, a field of ContactInfo
 */
@XmlRootElement(name = "Attribute")
public class Attribute implements Serializable {

    @XmlElement
    private int id;
    @XmlElement
    private String name;
    @XmlElement
    private String iconPath;

    public Attribute(int id, String name, String iconPath) {
        this.id = id;
        this.name = name;
        this.iconPath = iconPath;
    }

    public Attribute() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Attribute attribute = (Attribute) o;

        return name != null ? name.equals(attribute.name) : attribute.name == null;

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    public enum AttributeTypes {

    }
}
