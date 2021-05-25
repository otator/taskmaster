package com.example.taskmaster;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4ClassRunner.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityTestRule = new ActivityScenarioRule<>(MainActivity.class);

    // In order to run the following tests do the following
    // TODO: navigate to developer options from setting on your device and do the following:
    // TODO: turn off window animation scale
    // TODO: turn off transition animation scale

    @Test
    public void testUsernameTextIsDisplayed(){
        onView(withId(R.id.usernameTextView)).check(matches(isDisplayed()));
    }

    @Test
    public void testSettingIsDisplayed(){
        onView(withId(R.id.settingBtn)).check(matches(isDisplayed()));
    }

    @Test
    public void testAddTaskButtonIsDisplayed(){
        onView(withId(R.id.addBtn)).check(matches(isDisplayed()));
    }

    @Test
    public void testAllTasksIsDisplayed(){
        onView(withId(R.id.allTasksBtn)).check(matches(isDisplayed()));
    }

    @Test
    // NOTE: this test will pass only if you have at least on tasks add, this is according to the implementation of the Setting activity
    public void checkUsername(){
        onView(withId(R.id.settingBtn)).perform(click());
        onView(withId(R.id.usernameEditText2)).perform(replaceText("AbdalQader"), closeSoftKeyboard());
        onView(withId(R.id.numberOfTasksEditText)).perform(replaceText("1"), closeSoftKeyboard());
        onView(withId(R.id.saveBtn)).perform(click());
        onView(withId(R.id.usernameTextView)).check(matches(withText("AbdalQader's tasks")));
    }


    @Test
    public void checkTaskNameDisplayedAsTitle(){
        onView(withId(R.id.recyclerView)).perform(click());
    }
}
