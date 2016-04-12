package edu.cmu.java.development.application.resources;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by urbano on 4/2/16.
 */
@XmlRootElement(name = "Contact")
public class Contact {

    @XmlElement
    private String name;
    @XmlElement
    private ArrayList<ContactInfo> contactInfoArrayList;
    @XmlElement
    private String description;
    @XmlElement
    private Date expiration;
    @XmlElement
    private String photoUrl;

    @Override
    public String toString() {
        return "Contact{" +
                "name='" + name + '\'' +
                ", contactInfoArrayList=" + contactInfoArrayList +
                ", description='" + description + '\'' +
                ", expiration=" + expiration +
                ", photoUrl='" + photoUrl + '\'' +
                '}';
    }

    public Contact() {
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<ContactInfo> getContactInfoArrayList() {
        return contactInfoArrayList;
    }

    public void setContactInfoArrayList(ArrayList<ContactInfo> contactInfoArrayList) {
        this.contactInfoArrayList = contactInfoArrayList;
    }
}
