package com.example.pregnapp.auth.login

import android.content.res.Resources
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.rememberNavController
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.platform.app.InstrumentationRegistry
import com.example.pregnapp.MainActivity
import com.example.pregnapp.R
import com.example.pregnapp.ToastMatcher
import com.example.pregnapp.network.MockApiEngine
import com.example.pregnapp.ui.Tags
import com.example.pregnapp.ui.theme.PregnAppTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.ktor.http.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@HiltAndroidTest
class LoginScreenTest {

    private val res: Resources = InstrumentationRegistry.getInstrumentation().targetContext.resources

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp(){
        hiltRule.inject()
        MockApiEngine.givenSuccess()
        composeRule.activity.setContent {
            val navController = rememberNavController()
            PregnAppTheme {
                LoginScreen(navController = navController)
            }
        }
    }

    @Test
    fun openScreen_loginButtonNotClickable(){
        composeRule.onNodeWithText(res.getString(R.string.login)).assertExists()
        composeRule.onNodeWithText(res.getString(R.string.login)).assertIsNotEnabled()
    }

    @Test
    fun insertCorrectEmailAndPassword_loginButtonClickable(){
        composeRule.onNodeWithText(res.getString(R.string.email)).performTextInput("test@mail.com")
        composeRule.onNodeWithText(res.getString(R.string.password)).performTextInput("123456789")
        composeRule.onNodeWithText(res.getString(R.string.login)).assertIsEnabled()
    }

    @Test
    fun insertCorrectEmailAndIncorrectPassword_loginButtonNotClickable(){
        composeRule.onNodeWithText(res.getString(R.string.email)).performTextInput("test@mail.com")
        composeRule.onNodeWithText(res.getString(R.string.password)).performTextInput("123456")
        composeRule.onNodeWithText(res.getString(R.string.login)).assertIsNotEnabled()
    }

    @Test
    fun insertIncorrectEmailAndCorrectPassword_loginButtonNotClickable(){
        composeRule.onNodeWithText(res.getString(R.string.email)).performTextInput("mail.com")
        composeRule.onNodeWithText(res.getString(R.string.password)).performTextInput("12345678")
        composeRule.onNodeWithText(res.getString(R.string.login)).assertIsNotEnabled()
    }

    @Test
    fun insertIncorrectEmail_emailErrorDisplayed(){
        composeRule.onNodeWithText(res.getString(R.string.email)).performTextInput("mail.com")
        composeRule.onNodeWithText(res.getString(R.string.password)).performClick()
        composeRule.onNodeWithText(res.getString(R.string.incorrect_email)).assertIsDisplayed()
    }

    @Test
    fun insertIncorrectPassword_passwordErrorDisplayed(){
        composeRule.onNodeWithText(res.getString(R.string.password)).performTextInput("123456")
        composeRule.onNodeWithText(res.getString(R.string.email)).performClick()
        composeRule.onNodeWithText(res.getString(R.string.incorrect_password)).assertIsDisplayed()
    }

    @Test
    fun insertPassword_insertedPasswordIsHidden(){
        composeRule.onNodeWithText(res.getString(R.string.password)).performTextInput("123456")
        composeRule.onNodeWithTag(Tags.TAG_PASSWORD_HIDDEN).assertExists()
    }

    @Test
    fun apiThrowsBadRequestException_errorMessageIsDisplayed(){
        MockApiEngine.givenFailure(HttpStatusCode.BadRequest)
        composeRule.onNodeWithText(res.getString(R.string.email)).performTextInput("test@mail.com")
        composeRule.onNodeWithText(res.getString(R.string.password)).performTextInput("123456789")
        composeRule.onNodeWithText(res.getString(R.string.login)).performClick()
        onView(withText(R.string.bad_request)).inRoot(ToastMatcher()).check(matches(isDisplayed()))
        //Thread.sleep could be substituted with ToastManager in production code allowing Espresso to wait for it to disappear
        //Makes sure tests arent flaky but adds production code
        //https://stackoverflow.com/questions/31998205/is-it-possible-to-disable-toasts-or-wait-until-toast-disappears-while-testing
        Thread.sleep(Toast.LENGTH_LONG.toLong()*2000)
    }

    @Test
    fun apiThrowsInternalServerException_errorMessageIsDisplayed(){
        MockApiEngine.givenFailure(HttpStatusCode.InternalServerError)
        composeRule.onNodeWithText(res.getString(R.string.email)).performTextInput("test@mail.com")
        composeRule.onNodeWithText(res.getString(R.string.password)).performTextInput("123456789")
        composeRule.onNodeWithText(res.getString(R.string.login)).performClick()
        onView(withText(R.string.internal_server_error)).inRoot(ToastMatcher()).check(matches(isDisplayed()))
        //Thread.sleep could be substituted with ToastManager in production code allowing Espresso to wait for it to disappear
        //Makes sure tests arent flaky but adds production code
        //https://stackoverflow.com/questions/31998205/is-it-possible-to-disable-toasts-or-wait-until-toast-disappears-while-testing
        Thread.sleep(Toast.LENGTH_LONG.toLong()*2000)
    }

}