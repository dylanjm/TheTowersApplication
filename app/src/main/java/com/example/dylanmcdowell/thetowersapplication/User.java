package com.example.dylanmcdowell.thetowersapplication;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class User {
    private static final String TAG = "theTag";
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String email;
    private String firstName;
    private String lastName;
    private String aptNumber;
    private Boolean isStaff;
    private Boolean isLogged;

    //User(){setCurrentUser();}

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
    void login()   { setIsLogged(true);  }
    void signOut() { setIsLogged(false); }

    // Handle Current User
    void setCurrentUser() {
        FirebaseUser user = mAuth.getCurrentUser();
        if(user!=null) {
            final String username = user.getEmail().replace(".", "*%*");
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference();
            userRef.child("Users").child(username).addValueEventListener(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            User currentUser = dataSnapshot.getValue(User.class);
                            System.out.println(currentUser);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            System.out.println("Read Failed! " + databaseError.toException());
                        }
                    });
        }
    }

}
