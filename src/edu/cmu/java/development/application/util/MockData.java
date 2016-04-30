package edu.cmu.java.development.application.util;

import edu.cmu.java.development.application.resources.Attribute;
import edu.cmu.java.development.application.resources.Contact;
import edu.cmu.java.development.application.resources.ContactInfo;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by Wroger on 4/30/2016.
 */
public class MockData {

    public static Contact mockContact() {
        Contact c = new Contact();

        c.setExpiration(new Date(1464588270000L).getTime());
        c.setName("Prin");
        c.setDescription("CMU Student");
        c.setPhotoUrl("photoURL");
//        c.setId(13);

        Set<ContactInfo> al = new LinkedHashSet<ContactInfo>();
        ContactInfo c1 = new ContactInfo();
        Attribute a1 = new Attribute();
        a1.setName("Facebook");
        a1.setId(4);
        c1.setAttributeValue("Prin FB page");
        c1.setAttribute(a1);

        ContactInfo c2 = new ContactInfo();
        Attribute a2 = new Attribute();
        a2.setName("Location");
        a2.setId(10);
        c2.setAttributeValue("Pittsburgh");
        c2.setAttribute(a2);

        ContactInfo c3 = new ContactInfo();
        Attribute a3 = new Attribute();
        a3.setName("Email");
        a3.setId(2);
        c3.setAttributeValue("prin@email.com");
        c3.setAttribute(a3);

        al.add(c1);
        al.add(c2);
        al.add(c3);

        c.setContactInfoArrayList(al);

        return c;
    }

    public static Contact mockContact1() {
        Contact c = new Contact();

        c.setExpiration(new Date(1464588270000L).getTime());
        c.setName("urbano");
        c.setDescription("CMU-SV Student");
        c.setPhotoUrl("urbano photoURL");
//        c.setId(13);

        Set<ContactInfo> al = new LinkedHashSet<ContactInfo>();
        ContactInfo c1 = new ContactInfo();
        Attribute a1 = new Attribute();
        a1.setName("Facebook");
        a1.setId(4);
        c1.setAttributeValue("Urbanos FB page");
        c1.setAttribute(a1);

        ContactInfo c2 = new ContactInfo();
        Attribute a2 = new Attribute();
        a2.setName("Email");
        a2.setId(2);
        c2.setAttributeValue("oximer@gmail.com");
        c2.setAttribute(a2);

        ContactInfo c3 = new ContactInfo();
        Attribute a3 = new Attribute();
        a3.setName("Twitter");
        a3.setId(5);
        c3.setAttributeValue("@oximer");
        c3.setAttribute(a3);

        ContactInfo c4 = new ContactInfo();
        Attribute a4 = new Attribute();
        a4.setName("Website");
        a4.setId(9);
        c4.setAttributeValue("www.oximer.com");
        c4.setAttribute(a4);

        al.add(c1);
        al.add(c2);
        al.add(c3);
        al.add(c4);

        c.setContactInfoArrayList(al);

        return c;
    }

    public static Contact mockContact2() {
        Contact c = new Contact();

        c.setExpiration(new Date(1464588270000L).getTime());
        c.setName("Mita");
        c.setDescription("Google Employee");
        c.setPhotoUrl("mita photo");
//        c.setId(13);

        Set<ContactInfo> al = new LinkedHashSet<ContactInfo>();
        ContactInfo c1 = new ContactInfo();
        Attribute a1 = new Attribute();
        a1.setName("Facebook");
        a1.setId(4);
        c1.setAttributeValue("Mita FB page");
        c1.setAttribute(a1);

        ContactInfo c2 = new ContactInfo();
        Attribute a2 = new Attribute();
        a2.setName("Phone Number");
        a2.setId(3);
        c2.setAttributeValue("555-0123");
        c2.setAttribute(a2);

        ContactInfo c3 = new ContactInfo();
        Attribute a3 = new Attribute();
        a3.setName("Birthday");
        a3.setId(2);
        c3.setAttributeValue("01/01/92");
        c3.setAttribute(a3);

        al.add(c1);
        al.add(c2);
        al.add(c3);

        c.setContactInfoArrayList(al);

        return c;
    }
}
