package edu.cmu.java.development.application.util;

import edu.cmu.java.development.application.resources.Attribute;
import edu.cmu.java.development.application.resources.Contact;
import edu.cmu.java.development.application.resources.ContactInfo;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Wroger on 4/30/2016.
 */
public class MockData {

    public static Contact mockContact() {
        Contact c = new Contact();

        c.setExpiration(new Date(1464588270000L));
        c.setName("John");
        c.setDescription("test3");
        c.setPhotoUrl("test4");
        c.setId(13);

        ArrayList<ContactInfo> al = new ArrayList<ContactInfo>();
        ContactInfo c1 = new ContactInfo();
        Attribute a1 = new Attribute();
        a1.setName("Facebook");
        a1.setId(4);
        c1.setAttributeValue("myfbpage");
        c1.setAttribute(a1);

        ContactInfo c2 = new ContactInfo();
        Attribute a2 = new Attribute();
        a2.setName("Location");
        a2.setId(10);
        c2.setAttributeValue("pittsburgh");
        c2.setAttribute(a2);

        ContactInfo c3 = new ContactInfo();
        Attribute a3 = new Attribute();
        a3.setName("Email");
        a3.setId(2);
        c3.setAttributeValue("john@email.com");
        c3.setAttribute(a3);

        al.add(c1);
        al.add(c2);
        al.add(c3);

        c.setContactInfoArrayList(al);

        return c;
    }
}
