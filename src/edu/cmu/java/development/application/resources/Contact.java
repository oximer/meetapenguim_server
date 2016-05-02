package edu.cmu.java.development.application.resources;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.util.Set;

/**
 * Created by urbano on 4/2/16.
 */
@XmlRootElement(name = "Contact")
public class Contact {

    @XmlElement
    private Integer id;
    @XmlElement
    private String name;
    @XmlElement
    private Set<ContactInfo> contactInfoArrayList;
    @XmlElement
    private String description;
    @XmlElement
    private long expiration;
    @XmlElement
    private String photoUrl;

    @XmlElement
    private String zipCode = "94043";
    @XmlElement
    private int profilePicResId;


    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", contactInfoArrayList=" + contactInfoArrayList +
                ", description='" + description + '\'' +
                ", expiration=" + expiration +
                ", photoUrl='" + photoUrl + '\'' +
                '}';
    }

    public Contact() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    //Needed to convert from JSON in JAX-RS to object
    public static Contact fromString(String jsonString) {
        ObjectMapper mapper = new ObjectMapper();
        Contact c = null;

        try {
            c = mapper.readValue(jsonString, Contact.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return c;
    }



    public long getExpiration() {
        return expiration;
    }

    public void setExpiration(long expiration) {
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

    public Set<ContactInfo> getContactInfoArrayList() {
        return contactInfoArrayList;
    }

    public void setContactInfoArrayList(Set<ContactInfo> contactInfoArrayList) {
        this.contactInfoArrayList = contactInfoArrayList;
    }

    public int getProfilePicResId() {
        return profilePicResId;
    }

    public void setProfilePicResId(int profilePicResId) {
        this.profilePicResId = profilePicResId;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
