package com.example.dylanmcdowell.thetowersapplication;

/**
 * Created by Samuel Tew on 6/8/2016.
 * For all the user information to be stored and manipulated.
 */
public class User {

    public User(String first, String last, String apt,
         Boolean isStaf) {
        firstName = first;
        lastName = last;
        aptNumber = apt;
        isStaff = isStaf;
    }

    public User(String first, String last, String apt){
        firstName = first;
        lastName = last;
        aptNumber = apt;
    }


    private User() {
    }


    String firstName;
    String lastName;
    String aptNumber;
    Boolean isStaff;



    // accepts a MaintenanceRequest object as a parameter and sends it along.
    void submitRequest(Boolean Maint){
    // Add request to Maintenance users.
    }


    Message sendMessage(Message message) {
        // add message to the message stack.
        return message;
    }

    void resolveRequest(Boolean Maint) {
        //remove request from maintenance users.
    }

    void deleteMessage(Message message) {
        // Remove a message from the message stack
    }


    String getFirstName() {
        return firstName;
    }

    String getLastName() {
        return lastName;
    }

    String getAptNumber() {
        return aptNumber;
    }

}
