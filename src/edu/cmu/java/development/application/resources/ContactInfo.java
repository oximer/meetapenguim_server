package edu.cmu.java.development.application.resources;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.io.Serializable;

/**
 * Contact Info class.
 */
@XmlRootElement(name = "ContactInfo")
public class ContactInfo implements Serializable {

    @XmlElement
    private int id;

    @XmlElement
    private Attribute attribute;

    @XmlElement
    private String extraDescription;

    @XmlElement
    private String attributeValue;

    @XmlElement
    private boolean editing;

    public ContactInfo() {
    }

    public static ContactInfo fromString(String jsonString) {
        ObjectMapper mapper = new ObjectMapper();
        ContactInfo c = null;

        try {
            c = mapper.readValue(jsonString, ContactInfo.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return c;
    }

    public ContactInfo(Attribute attribute, String extraDescription, String attributeValue) {
        this.attribute = attribute;

        this.extraDescription = extraDescription;
        this.attributeValue = attributeValue;
        this.editing = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
