package edu.cmu.java.development.application.resources;

/**
 * Created by Prin Oungpasuk on 4/3/2016.
 */
public class InboxMessage {

    private Integer cloudId;
    private Contact contact;
    private String message;
    private long timeStamp;


    private boolean status;

    /**
     * default no-args constructor.
     */
    public InboxMessage() {

    }

    public Integer getCloudId() {
        return cloudId;
    }

    public void setCloudId(Integer cloudId) {
        this.cloudId = cloudId;
    }

    /**
     * Getter for contact.
     *
     * @return Contact.
     */
    public Contact getContact() {
        return contact;
    }

    /**
     * Getter for message.
     *
     * @return String message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Getter for time stamp.
     *
     * @return String time stamp.
     */
    public long getTimeStamp() {
        return timeStamp;
    }

    /**
     * Setter for Contact.
     *
     * @param contact Contact to set.
     */
    public void setContact(Contact contact) {
        this.contact = contact;
    }

    /**
     * Setter for message.
     *
     * @param message Message to set.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Setter for time stamp.
     *
     * @param timeStamp Time stamp to set.
     */
    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
