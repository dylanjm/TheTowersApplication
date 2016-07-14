package com.example.dylanmcdowell.thetowersapplication;

public class Request {
    private String subject;
    private String body;
    private String sender;
    private Boolean isEmergency;
    private String timeStamp;
    private String aptNumber;
    private String key;

    @Override
    public String toString() {
        return "\n"
                + this.subject + this.getEmergencyMessage()
                + "\n"
                + this.aptNumber
                + "\n"
                + this.timeStamp.replace("_", " ")
                + "\n"
                + "From: " + this.sender
                + "\n";
    }

    //Default Constructor
    Request() {}

    //Non-Default Constructor
    public Request(String theSubject, String theBody, String theSender,
                   Boolean emergency, String aptNumber, String time) {
        this.subject     = theSubject;
        this.body        = theBody;
        this.sender      = theSender;
        this.isEmergency = emergency;
        this.timeStamp   = time;
        this.aptNumber   = aptNumber;
    }

    //Getters
    public String getSubject()      { return subject;     }
    public String getBody()         { return body;        }
    public String getSender()       { return sender;      }
    public Boolean getIsEmergency() { return isEmergency; }
    public String getTimeStamp()    { return timeStamp;   }
    public String getAptNumber()    { return aptNumber;   }
    public String getKey()          { return key;         }
    public String getEmergencyMessage(){
        if (isEmergency)
            return " -Emergency!!!";
        else
            return "";
    }

    //Setters
    public void setKey(String theKey) { key = theKey; }
}


