package com.jnu.student;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import static java.lang.Thread.sleep;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;

import com.jnu.student.myclass.ShopItem;
import com.jnu.student.myclass.myDateSave;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    myDateSave dataSaverBackup;
    ArrayList<ShopItem> shopItemsBackup;
    @Before
    public void setUp() throws Exception {
        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        dataSaverBackup = new myDateSave(targetContext);
        shopItemsBackup = dataSaverBackup.load();

        dataSaverBackup.save(new ArrayList<>());
    }

    @After
    public void tearDown() throws Exception {
        dataSaverBackup.save(shopItemsBackup);
    }



    @Test
    public void mainActivityTest() {

        try(ActivityScenario<MainActivity>  scenario = ActivityScenario.launch(MainActivity.class)) {
            scenario.onActivity(activity -> {
            });
            //此处贴测试代码
            // 检查my food不存在
            ViewInteraction textView2 = onView(
                    allOf(withId(R.id.name), withText("my food"),
                            withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                            isDisplayed()));
            textView2.check(doesNotExist());

            // 长按白菜
            ViewInteraction textView3 = onView(
                    allOf(withId(R.id.name), withText("白菜"),
                            withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                            isDisplayed()));
            textView3.check(matches(isDisplayed()));

            ViewInteraction recyclerView = onView(
                    allOf(withId(R.id.recyclerView),
                            childAtPosition(
                                    withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                    0)));
            recyclerView.perform(actionOnItemAtPosition(0, longClick()));

            try {
                sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            // 点击增加菜单
            ViewInteraction materialTextView = onView(
                    allOf(withId(android.R.id.title), withText("增加"),
                            childAtPosition(
                                    childAtPosition(
                                            withId(android.R.id.content),
                                            0),
                                    0),
                            isDisplayed()));
            materialTextView.perform(click());

            try {
                sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            // 填写名称为my food
            ViewInteraction appCompatEditText = onView(
                    allOf(withId(R.id.name),
                            childAtPosition(
                                    childAtPosition(
                                            withId(android.R.id.content),
                                            0),
                                    3),
                            isDisplayed()));
            appCompatEditText.perform(replaceText("my food "), closeSoftKeyboard());

            // 填写价格为2.12
            ViewInteraction appCompatEditText2 = onView(
                    allOf(withId(R.id.price),
                            childAtPosition(
                                    childAtPosition(
                                            withId(android.R.id.content),
                                            0),
                                    5),
                            isDisplayed()));
            appCompatEditText2.perform(replaceText("2.12"), closeSoftKeyboard());

            ViewInteraction appCompatEditText3 = onView(
                    allOf(withId(R.id.price), withText("2.12"),
                            childAtPosition(
                                    childAtPosition(
                                            withId(android.R.id.content),
                                            0),
                                    5),
                            isDisplayed()));
            appCompatEditText3.perform(pressImeActionButton());

            // 点击确认按钮
            ViewInteraction materialButton = onView(
                    allOf(withId(R.id.true_button), withText("确认"),
                            childAtPosition(
                                    childAtPosition(
                                            withId(android.R.id.content),
                                            0),
                                    1),
                            isDisplayed()));
            materialButton.perform(click());

            // 检查my food已显示在主界面
            ViewInteraction textView4 = onView(
                    allOf(withId(R.id.name), withText("my food "),
                            withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                            isDisplayed()));
            textView4.check(matches(isDisplayed()));

            // 检查2.12已显示在主界面
            ViewInteraction textView5 = onView(
                    allOf(withId(R.id.price), withText("2.12"),
                            withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                            isDisplayed()));
            textView5.check(matches(withText("2.12")));
            scenario.close();

        }
        //原来函数内部代码移到上面
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
