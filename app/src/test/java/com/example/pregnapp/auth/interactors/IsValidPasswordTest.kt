package com.example.pregnapp.auth.interactors

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class IsValidPasswordTest {

    private lateinit var isValidPassword : IsValidPassword

    @Before
    fun setUp(){
        isValidPassword = IsValidPassword()
    }

    @Test
    fun passwordBelow8Characters_returnsFalse(){
        assertEquals(false, isValidPassword("1234567"))
    }

    @Test
    fun passwordAboveOrEqual8Characters_returnsTrue(){
        assertEquals(true, isValidPassword("12345678"))
    }

    @Test
    fun passwordAbove50Characters_returnsFalse(){
        //51 character password
        val longPassword = "Q:zV~exWi#~wGpB=#JU+G0h3:p7~0g3_vb3Gf*eMBdCmba7uRZp"
        assertEquals(false, isValidPassword(longPassword))
    }
}