package com.example.dylanmcdowell.thetowersapplication;

/**
 * Created by Sam on 2016-06-27.
 */
public class Request {
        private String subject;
        private String body;
        private String author;
        private Boolean isEmergency;

        Request(String theSubject, String theBody, String theAuthor, Boolean emergency) {
            this.subject = theSubject;
            this.body = theBody;
            this.author = theAuthor;
            this.isEmergency = emergency;
        }

        @SuppressWarnings("unused")
        private Request() {
        }

        public String getSubject(){
            return subject;
        }

        public String getBody(){
            return body;
        }

        public String getAuthor() { return author; }

        public Boolean getIsEmergency() { return isEmergency; }
}

