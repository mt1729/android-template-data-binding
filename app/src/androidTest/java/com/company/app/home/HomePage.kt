package com.company.app.home

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.company.app.R

object HomePage {
    val welcomeMessage: ViewInteraction
        get() = onView(withId(R.id.home_welcome_message))
}
