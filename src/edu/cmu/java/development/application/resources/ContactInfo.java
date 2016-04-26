package edu.cmu.java.development.application.resources;

import java.io.Serializable;

/**
 * Contact Info class.
 */
public class ContactInfo implements Serializable {

    private Attribute attribute;
    private String extraDescription;
    private String attributeValue;
    private boolean editing;

    public ContactInfo() {
    }

    public ContactInfo(Attribute attribute, String extraDescription, String attributeValue) {
        this.attribute = attribute;

        this.extraDescription = extraDescription;
        this.attributeValue = attributeValue;
        this.editing = false;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public String getExtraDescription() {
        return extraDescription;
    }

    public String getAttributeName() {
        return getAttribute().getName();
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public boolean isEditing() {
        return editing;
    }

    public void setEditing(boolean editing) {
        this.editing = editing;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }
}
