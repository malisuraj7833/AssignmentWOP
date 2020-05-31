package com.example.assignment;

import com.example.assignment.ui.main.EmailAndPasswordValidator;
import static org.junit.Assert.*;


import org.junit.Test;

public class LoginUnitTests {
    @Test
    public void emailValidator_CorrectEmailType()
    {
        assertTrue(EmailAndPasswordValidator.Companion.isValidEmail("test@worldofplay.com"));
    }

    // Without .com
    @Test
    public void emailValidator_EmailTypeWithoutCom()
    {
        assertFalse(EmailAndPasswordValidator.Companion.isValidEmail("test@worldofplay"));
    }

    //With extra characters
    @Test
    public void emailValidator_EmailTypeWithExtraCharacters()
    {
        assertFalse(EmailAndPasswordValidator.Companion.isValidEmail("test@worldofplay...com"));
    }

    // With no username
    @Test
    public void emailValidator_EmailTypeWithNoUserName()
    {
        assertFalse(EmailAndPasswordValidator.Companion.isValidEmail("@worldofplay.com"));
    }

    // Empty Input
    @Test
    public void emailValidator_EmailTypeWithEmptyEmail()
    {
        assertFalse(EmailAndPasswordValidator.Companion.isValidEmail(""));
    }

    // Correct password as per rule
    @Test
    public void passwordValidator_CorrectPasswordType()
    {
        assertTrue(EmailAndPasswordValidator.Companion.isValidPassword("Worldofplay@2020"));
    }

    // no capital chars
    @Test
    public void passwordValidator_InCorrectPasswordTypeNoCapitalLetter()
    {
        assertFalse(EmailAndPasswordValidator.Companion.isValidPassword("worldofplay@2020"));
    }

    // no special chars
    @Test
    public void passwordValidator_InCorrectPasswordTypeNoSpecialCharacter()
    {
        assertFalse(EmailAndPasswordValidator.Companion.isValidPassword("Worldofplay2020"));
    }

    // no numbers
    @Test
    public void passwordValidator_InCorrectPasswordTypeNoNumbers()
    {
        assertFalse(EmailAndPasswordValidator.Companion.isValidPassword("Worldofplay@asd"));
    }
}
