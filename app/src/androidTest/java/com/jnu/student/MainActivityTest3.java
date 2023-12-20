package com.jnu.student;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withTagValue;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.Is.is;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest3 {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void mainActivityTest3() {
        ViewInteraction imageView = onView(
                allOf(withId(R.id.imageView), withContentDescription("商品图示"),
                        withTagValue(is(R.drawable.bai_cai)),
                        withParent(withParent(withId(R.id.recyclerView))),
                        isDisplayed()));
//        imageView.check(doesNotExist());
        imageView.check(matches(isDisplayed()));

//        ViewInteraction imageView2 = onView(
//                allOf(withId(R.id.imageView), withContentDescription("商品图示"),
//                        withParent(withParent(withId(R.id.recyclerView))),
//                        isDisplayed()));
//        imageView2.check(doesNotExist());
//        imageView2.check(matches(isDisplayed()));

//        ViewInteraction imageView3 = onView(
//                allOf(withId(R.id.imageView), withContentDescription("商品图示"),
//                        withParent(withParent(withId(R.id.recyclerView))),
//                        isDisplayed()));
//        imageView3.check(doesNotExist());
    }

}
