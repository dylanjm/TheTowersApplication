package com.example.dylanmcdowell.thetowersapplication;

/**
 * Created by Sam on 2016-06-27.
 * Request class. Contains a sender, a subject heading, a main body, and a Boolean value to indicate
 * whether or not it is an emergency
 * @version 1.0
 * @author Sam Ciavardini
 */
public class Request {
        private String subject;
        private String body;
        private String sender;
        private Boolean isEmergency;


    /**
     * A public constructor for a request object.
     * @author Sam Ciavardini
     * @version 1.0
     * @param theSubject
     * @param theBody
     * @param theSender
     * @param emergency
     */
        public Request(String theSubject, String theBody, String theSender, Boolean emergency) {
            this.subject = theSubject;
            this.body = theBody;
            this.sender = theSender;
            this.isEmergency = emergency;
        }

        @SuppressWarnings("unused")
        private Request() {
        }

    /**
     * getter for subject
     * @return subject
     */
        public String getSubject(){
            return subject;
        }

    /**
     * getter for body
     * @return body
     */
        public String getBody(){
            return body;
        }

    /**
     *  getter for author
     * @return author
     */
        public String getSender() { return sender; }

    /**
     * getter for emergency status
     * @return isEmergency
     */
        public Boolean getIsEmergency() { return isEmergency; }
}

