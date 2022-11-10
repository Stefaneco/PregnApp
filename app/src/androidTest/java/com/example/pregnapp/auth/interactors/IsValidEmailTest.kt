package com.example.pregnapp.auth.interactors

import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class IsValidEmailTest {

    private var isValidEmail: IsValidEmail = IsValidEmail()

    @Before
    fun setUp(){
        isValidEmail = IsValidEmail()
    }

    @Test
    fun correctEmailFormat_returnsTrue(){
        assertEquals(true, isValidEmail("correct@email.com"))
    }

    @Test
    fun emailWithoutDot_returnsFalse(){
        assertEquals(false, isValidEmail("correct@emailcom"))
    }

    @Test
    fun emailWithoutAt_returnsFalse(){
        assertEquals(false, isValidEmail("correctemail.com"))
    }

    @Test
    fun emailOver254Characters_returnsFalse(){
        //255 character email
        val longEmail = """cool-english-alphabet-loverer-abcdefghijklmnopqrstuvwxyz@try-to.send-
            |me-an-email-if-you-can-possibly-begin-to-remember-this-coz.this-is-the-longest-email-
            |address-known-to-man-but-to-bee-honest.this-is-such-a-stupidly-long-sub-domain-it
            |-forever.pacraig.com""".trimMargin()
        assertEquals(false, isValidEmail(longEmail))
    }

    @Test
    fun emailWithoutUsername_returnsFalse(){
        assertEquals(false, isValidEmail("@email.com"))
    }

    @Test
    fun emailWithoutDomainName_returnsFalse(){
        assertEquals(false, isValidEmail("correct@.com"))
    }

    @Test
    fun emailWithoutDomainRegister_returnsFalse(){
        assertEquals(false, isValidEmail("correct@email."))
    }


    @Test
    fun emptyEmail_returnsFalse(){
        assertEquals(false, isValidEmail(""))
    }


}