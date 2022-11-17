package com.example.pregnapp.auth.register

import android.content.res.Resources
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.rememberNavController
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import com.example.pregnapp.MainActivity
import com.example.pregnapp.R
import com.example.pregnapp.ToastMatcher
import com.example.pregnapp.network.MockApiEngine
import com.example.pregnapp.ui.theme.PregnAppTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.ktor.http.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@HiltAndroidTest
class RegisterScreenTest {


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
                RegisterScreen(navController = navController)
            }
        }
    }

    @Test
    fun openScreen_registerButtonNotClickable(){
        composeRule.onNodeWithText(res.getString(R.string.register)).assertExists()
        composeRule.onNodeWithText(res.getString(R.string.register)).assertIsNotEnabled()
    }

    @Test
    fun insertCorrectNameEmailAndPassword_registerButtonClickable(){
        composeRule.onNodeWithText(res.getString(R.string.name)).performTextInput("Alex")
        composeRule.onNodeWithText(res.getString(R.string.email)).performTextInput("test@mail.com")
        composeRule.onNodeWithText(res.getString(R.string.password)).performTextInput("123456789")
        composeRule.onNodeWithText(res.getString(R.string.register)).assertIsEnabled()
    }

    @Test
    fun insertCorrectNameEmailAndIncorrectPassword_loginButtonNotClickable(){
        composeRule.onNodeWithText(res.getString(R.string.name)).performTextInput("Alex")
        composeRule.onNodeWithText(res.getString(R.string.email)).performTextInput("test@mail.com")
        composeRule.onNodeWithText(res.getString(R.string.password)).performTextInput("123456")
        composeRule.onNodeWithText(res.getString(R.string.register)).assertIsNotEnabled()
    }

    @Test
    fun insertIncorrectEmailAndCorrectNamePassword_loginButtonNotClickable(){
        composeRule.onNodeWithText(res.getString(R.string.name)).performTextInput("Alex")
        composeRule.onNodeWithText(res.getString(R.string.email)).performTextInput("mail.com")
        composeRule.onNodeWithText(res.getString(R.string.password)).performTextInput("12345678")
        composeRule.onNodeWithText(res.getString(R.string.register)).assertIsNotEnabled()
    }

    @Test
    fun insertCorrectEmailPasswordAndIncorrectName_registerButtonClickable(){
        composeRule.onNodeWithText(res.getString(R.string.name)).performTextInput("A")
        composeRule.onNodeWithText(res.getString(R.string.email)).performTextInput("test@mail.com")
        composeRule.onNodeWithText(res.getString(R.string.password)).performTextInput("123456789")
        composeRule.onNodeWithText(res.getString(R.string.register)).assertIsNotEnabled()
    }

    @Test
    fun apiThrowsInternalServerException_errorMessageIsDisplayed(){
        MockApiEngine.givenFailure(HttpStatusCode.InternalServerError)
        composeRule.onNodeWithText(res.getString(R.string.name)).performTextInput("Alex")
        composeRule.onNodeWithText(res.getString(R.string.email)).performTextInput("test@mail.com")
        composeRule.onNodeWithText(res.getString(R.string.password)).performTextInput("123456789")
        composeRule.onNodeWithText(res.getString(R.string.register)).performClick()
        Espresso.onView(ViewMatchers.withText(R.string.internal_server_error))
            .inRoot(ToastMatcher()).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        //Thread.sleep could be substituted with ToastManager in production code allowing Espresso to wait for it to disappear
        //Makes sure tests arent flaky but adds production code
        //https://stackoverflow.com/questions/31998205/is-it-possible-to-disable-toasts-or-wait-until-toast-disappears-while-testing
        Thread.sleep(Toast.LENGTH_LONG.toLong()*2000)
    }

    @Test
    fun apiThrowsBadRequest_errorMessageIsDisplayed(){
        MockApiEngine.givenFailure(HttpStatusCode.BadRequest)
        composeRule.onNodeWithText(res.getString(R.string.name)).performTextInput("Alex")
        composeRule.onNodeWithText(res.getString(R.string.email)).performTextInput("test@mail.com")
        composeRule.onNodeWithText(res.getString(R.string.password)).performTextInput("123456789")
        composeRule.onNodeWithText(res.getString(R.string.register)).performClick()
        Espresso.onView(ViewMatchers.withText(R.string.bad_request))
            .inRoot(ToastMatcher()).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        //Thread.sleep could be substituted with ToastManager in production code allowing Espresso to wait for it to disappear
        //Makes sure tests arent flaky but adds production code
        //https://stackoverflow.com/questions/31998205/is-it-possible-to-disable-toasts-or-wait-until-toast-disappears-while-testing
        Thread.sleep(Toast.LENGTH_LONG.toLong()*2000)
    }


}