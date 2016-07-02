package com.example.dylanmcdowell.thetowersapplication;

/**
 * Created by Samuel Tew on 6/8/2016.
 * For all the user information to be stored and manipulated.
 */
public class User {

    User(String first, String last, String apt,
         Boolean isMaint, Boolean isMan, Boolean isTen, Boolean islog) {
        firstName = first;
        lastName = last;
        aptNumber = apt;
        isMaintenance = isMaint;
        isManager = isMan;
        isTenant = isTen;
        logged = islog;
    }


    String firstName;
    String lastName;
    String aptNumber;
    Boolean isManager;
    Boolean isMaintenance;
    Boolean isTenant;

    ////////////////////////  Maybe?  //////////////////////////
    // Boolean determining  whether one is logged in or not.?
    Boolean logged;
    // Stack messages
    // Stack requests
    ////////////////////////////////////////////////////////////

    // accepts a MaintenanceRequest object as a parameter and sends it along.
    void submitRequest(Boolean Maint){
    // Add request to Maintenance users.
    }

    // Changes the user's status to logged in.
    void login() {
        logged = true;
    }

    // Changes user status to logged out
    void signOut() {
        logged = false;
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
