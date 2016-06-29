package com.example.dylanmcdowell.thetowersapplication;

/**
 * Created by Sam on 2016-06-08.
 */
public class Message {
    private String subject;
    private String body;
    private String recipient;
    private String author;

    Message(String theSubject, String theBody, String theAuthor, String theRecipient) {
        this.subject = theSubject;
        this.body = theBody;
        this.author = theAuthor;
        this.recipient = theRecipient;
    }

    @SuppressWarnings("unused")
    private Message() {
    }

    public String getSubject(){
        return subject;
    }

    public String getBody(){
        return body;
    }

    public String getAuthor() { return author; }

    public String getRecipient() { return recipient; }

}
