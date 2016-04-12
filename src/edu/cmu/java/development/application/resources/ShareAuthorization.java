package edu.cmu.java.development.application.resources;

/**
 * Created by urbano on 4/11/16.
 */
public class ShareAuthorization {

    private String authorizationMessage;

    public ShareAuthorization(String authorizationMessage) {
        this.authorizationMessage = authorizationMessage;
    }

    public String getAuthorizationMessage() {
        return authorizationMessage;
    }

    public void setAuthorizationMessage(String authorizationMessage) {
        this.authorizationMessage = authorizationMessage;
    }
}
