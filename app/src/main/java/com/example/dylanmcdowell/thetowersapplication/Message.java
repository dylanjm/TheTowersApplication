package com.example.dylanmcdowell.thetowersapplication;

/**
 * Created by Sam on 2016-06-08.
 */
public class Message {
    private String subject;
    private String body;
    private String author;

    Message(String body, String subject, String author) {
        this.subject = subject;
        this.body = body;
        this.author = author;
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

}
