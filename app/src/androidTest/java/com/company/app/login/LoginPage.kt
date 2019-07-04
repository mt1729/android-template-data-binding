package com.company.app.login

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.company.app.R

object LoginPage {
    val usernameInput: ViewInteraction
        get() = onView(withId(R.id.login_username))

    val passwordInput: ViewInteraction
        get() = onView(withId(R.id.login_password))

    val loginButton: ViewInteraction
        get() = onView(withId(R.id.login_button))

    val snackbar: ViewInteraction
        get() = onView(withId(com.google.android.material.R.id.snackbar_text))

    fun login(username: String, password: String) {
        usernameInput.perform(clearText()).perform(typeText(username))
        passwordInput.perform(clearText()).perform(typeText(password))

        // TODO: - Use IMEAction action to click software keyboard instead
        closeSoftKeyboard()

        loginButton.perform(click())
    }
}