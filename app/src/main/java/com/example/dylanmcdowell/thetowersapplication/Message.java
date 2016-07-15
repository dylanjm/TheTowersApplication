package com.example.dylanmcdowell.thetowersapplication;

/**
 * Created by Sam on 2016-06-08.
 * Message class. Contains a sender, a subject heading, a main body, and a recipient.
 * @version 1.0
 * @author Sam Ciavardini
 */
public class Message {
    private String subject;
    private String body;
    private String recipient;
    private String sender;

    @Override
    public String toString() {
        return "\n" + this.subject + "\n\nFrom: " + this.sender + "\n";
    }

    /**
     * Public Constructor for a message object
     * @author Sam Ciavardini
     * @version 1.0
     * @param theSubject
     * @param theBody
     * @param theSender
     * @param theRecipient
     */
    public Message(String theSubject, String theBody, String theSender, String theRecipient) {
        this.subject = theSubject;
        this.body = theBody;
        this.sender = theSender;
        this.recipient = theRecipient;
    }

    @SuppressWarnings("unused")
    private Message() {
    }

    /**
     * Getter for the subject
     * @author Sam Ciavardini
     * @return subject
     */
    public String getSubject(){
        return subject;
    }

    /**
     * Getter for the body
     * @author Sam Ciavardini
     * @return body
     */
    public String getBody(){
        return body;
    }

    /**
     * Getter for the sender
     * @author Sam Ciavardini
     * @return sender
     */
    public String getSender() { return sender; }

    /**
     * Getter for the recipient
     * @author Sam Ciavardini
     * @return recipient
     */
    public String getRecipient() { return recipient; }

    public void setRecipient(String theRecipient) {recipient = theRecipient;}

}
