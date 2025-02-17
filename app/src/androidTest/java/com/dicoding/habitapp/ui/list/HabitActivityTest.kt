package com.dicoding.habitapp.ui.list

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.action.ViewActions.click
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.assertion.ViewAssertions.matches
import com.dicoding.habitapp.R
import com.dicoding.habitapp.ui.add.AddHabitActivity
import org.junit.Test
import org.junit.runner.RunWith

//TODO 16 : Write UI test to validate when user tap Add Habit (+), the AddHabitActivity displayed
@RunWith(AndroidJUnit4::class)
class HabitActivityTest {

    @Before
    fun setup() {
        Intents.init()
        ActivityScenario.launch(HabitListActivity::class.java)
    }

    @Test
    fun whenHabitActivityDisplayed() {
        onView(withId(R.id.fab))
            .check(matches(isDisplayed()))
            .perform(click())

        Intents.intended(hasComponent(AddHabitActivity::class.java.name))
    }
}