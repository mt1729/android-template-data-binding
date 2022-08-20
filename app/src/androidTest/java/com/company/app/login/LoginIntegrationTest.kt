package com.company.app.login

import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.company.app.R
import com.company.app.app.MainActivity
import com.company.app.app.PreferenceStore
import com.company.app.home.HomePage
import com.jakewharton.espresso.OkHttp3IdlingResource
import okhttp3.OkHttpClient
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.koin.test.inject

@RunWith(AndroidJUnit4::class)
class LoginIntegrationTest : KoinTest {
    private val prefs: PreferenceStore by inject()
    private val httpClient: OkHttpClient by inject()

    private var idlingResource: OkHttp3IdlingResource? = null

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun before() {
        // Reset state of app through this callback
        activityRule.scenario.onActivity {
            prefs.username = null
        }

        idlingResource = OkHttp3IdlingResource.create("OkHttp", httpClient)
        IdlingRegistry.getInstance().register(idlingResource)
    }

    @After
    fun after() {
        activityRule.scenario.close()

        IdlingRegistry.getInstance().unregister(idlingResource)
    }

    @Test
    fun loginFailure() {
        LoginPage.login("badUsername", "badPassword")

        LoginPage.snackbar.check(
            matches(withText(R.string.login_failed))
        )
    }

    @Test
    fun loginSuccess() {
        LoginPage.login("testUsername", "testPassword")

        HomePage.welcomeMessage.check(
            matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE))
        )
    }
}
