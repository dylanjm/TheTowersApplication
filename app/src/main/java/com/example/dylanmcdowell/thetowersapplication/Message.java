package com.example.dylanmcdowell.thetowersapplication;

/**
 * Created by Sam on 2016-06-08.
 */
public class Message {
    private String messageSubject;
    private String messageBody;

    public String getMessageSubject(){
        return messageSubject;
    }

    public String getMessageBody(){
        return messageBody;
    }

    public Message(String theSubject){
        messageSubject = theSubject;
    }
}
