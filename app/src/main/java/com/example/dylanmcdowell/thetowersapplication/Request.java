package com.example.dylanmcdowell.thetowersapplication;

/**
 * Created by Sam on 2016-06-27.
 */
public class Request {
        private String subject;
        private String body;
        private String author;
        private Boolean isEmergency;


    /**
     * A public constructor for a request object.
     * @author Sam Ciavardini
     * @version 1.0
     * @param theSubject
     * @param theBody
     * @param theAuthor
     * @param emergency
     */
        public Request(String theSubject, String theBody, String theAuthor, Boolean emergency) {
            this.subject = theSubject;
            this.body = theBody;
            this.author = theAuthor;
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
        public String getAuthor() { return author; }

    /**
     * getter for emergency status
     * @return isEmergency
     */
        public Boolean getIsEmergency() { return isEmergency; }
}

