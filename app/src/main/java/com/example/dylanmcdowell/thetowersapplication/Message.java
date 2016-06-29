package com.example.dylanmcdowell.thetowersapplication;

/**
 * Created by Sam on 2016-06-08.
 */
public class Message {
    private String subject;
    private String body;
    private String recipient;
    private String author;

    /**
     * Public Constructor for a message object
     * @author Sam Ciavardini
     * @version 1.0
     * @param theSubject
     * @param theBody
     * @param theAuthor
     * @param theRecipient
     */
    public Message(String theSubject, String theBody, String theAuthor, String theRecipient) {
        this.subject = theSubject;
        this.body = theBody;
        this.author = theAuthor;
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
     * Getter for the author
     * @author Sam Ciavardini
     * @return author
     */
    public String getAuthor() { return author; }

    /**
     * Getter for the recipient
     * @author Sam Ciavardini
     * @return recipient
     */
    public String getRecipient() { return recipient; }

}
