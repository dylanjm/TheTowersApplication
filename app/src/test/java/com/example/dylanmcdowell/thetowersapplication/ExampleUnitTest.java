package com.example.dylanmcdowell.thetowersapplication;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void isMessageString() throws Exception {
        Message testMessage = new Message("Test Subject");
        assertEquals(testMessage.getMessageSubject(), "Test Subject");
    }
    @Test
    public void isBodyString() throws Exception {
        Message testMessage = new Message ("YOU DIED");
        assertNotNull(testMessage);
    }
    @Test
    public void isUsernameString() throws Exception{
        SignInActivity signIn = new SignInActivity();
        signIn.setUsername("LEET_HAXOR");
        assertNotNull(signIn.getUsername());
    }
    @Test
    public void isPasswordString() throws Exception{
        SignInActivity signIn = new SignInActivity();
        signIn.setPassword("ILOVECHIUAHAS");
        assertNotNull(signIn.getPassword());
    }
}