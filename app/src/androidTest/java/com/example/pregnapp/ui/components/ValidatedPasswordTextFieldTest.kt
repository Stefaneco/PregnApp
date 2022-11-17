package com.example.pregnapp.ui.components

import android.content.res.Resources
import androidx.compose.material.TextField
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import com.example.pregnapp.R
import com.example.pregnapp.ui.Tags
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ValidatedPasswordTextFieldTest {

    private val res: Resources = InstrumentationRegistry.getInstrumentation().targetContext.resources

    @get:Rule
    val composeRule = createComposeRule()

    @Before
    fun setUp(){
        composeRule.setContent{
            ValidatedPasswordTextField(hint = "Password", isFieldValid = { true }, errorMessage = "Error")
            TextField(value = "TEST", onValueChange = {})
        }
    }

    @Test
    fun insertPasswordAndTogglePasswordVisibility_insertedPasswordIsVisible(){
        composeRule.onNodeWithText(res.getString(R.string.password)).performTextInput("123456")
        composeRule.onNodeWithTag(Tags.TAG_PASSWORD_HIDDEN_ICON).performClick()
        composeRule.onNodeWithTag(Tags.TAG_PASSWORD_VISIBLE).assertExists()
    }

    @Test
    fun insertPasswordAndTogglePasswordVisibilityOnAndOff_insertedPasswordIsHidden(){
        composeRule.onNodeWithText(res.getString(R.string.password)).performTextInput("123456")
        composeRule.onNodeWithTag(Tags.TAG_PASSWORD_HIDDEN_ICON).performClick()
        composeRule.onNodeWithTag(Tags.TAG_PASSWORD_VISIBLE).assertExists()
        composeRule.onNodeWithTag(Tags.TAG_PASSWORD_VISIBLE_ICON).performClick()
        composeRule.onNodeWithTag(Tags.TAG_PASSWORD_HIDDEN).assertExists()
    }
}