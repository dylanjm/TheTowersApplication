package com.example.dylanmcdowell.thetowersapplication;

import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class User {
    //Logistic Variables
    private static final String TAG = "theTag";
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    //Member Variables
    private String email;
    private String firstName;
    private String lastName;
    private String aptNumber;
    private Boolean isStaff;
    private Boolean isLogged;

    User(){}

    User(String email, String first,
         String last, String aptNum,
         Boolean staff, Boolean logged){
        setEmail(email);
        setFirstName(first);
        setLastName(last);
        setAptNumber(aptNum);
        setIsStaff(staff);
        setIsLogged(logged);
    }

    // Getters
    String getEmail()     { return email;     }
    String getLastName()  { return lastName;  }
    String getFirstName() { return firstName; }
    String getAptNumber() { return aptNumber; }
    Boolean getIsLogged() { return isLogged;  }
    Boolean getIsStaff()  { return isStaff;   }

    // Setters
    void setEmail(String email)      { this.email = email;      }
    void setLastName(String last)    { this.lastName = last;    }
    void setFirstName(String first)  { this.firstName = first;  }
    void setAptNumber(String aptNum) { this.aptNumber = aptNum; }
    void setIsStaff(Boolean staff)   { this.isStaff = staff;    }
    void setIsLogged(Boolean logged) { this.isLogged = logged;  }

    // Handle Login State
    void signOut() {
        setIsLogged(false);
        String databaseEmail = this.getEmail().replace(".", "*%*");
        String base = "Users/" + databaseEmail +"/isLogged";
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference(base);
        myRef.setValue(getIsLogged());
    }
}
