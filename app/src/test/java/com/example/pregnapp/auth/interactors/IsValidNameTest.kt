package com.example.pregnapp.auth.interactors

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class IsValidNameTest {

    private lateinit var isValidName : IsValidName

    @Before
    fun setUp(){
        isValidName = IsValidName()
    }

    @Test
    fun nameBetween2To50Character_returnsTrue(){
        assertEquals(true, isValidName("Abc"))
    }

    @Test
    fun nameAbove50Characters_returnsFalse(){
        assertEquals(false, isValidName(
            "AbcdeAbcdeAbcdeAbcdeAbcdeAbcdeAbcdeAbcdeAbcdeAbcdef")
        )
    }

    @Test
    fun nameBelow2Characters_returnsFalse(){
        assertEquals(false, isValidName("A"))
    }
}