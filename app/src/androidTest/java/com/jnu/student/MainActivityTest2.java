package com.jnu.student;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withTagValue;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertTrue;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest2 {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void mainActivityTest2() {
        ViewInteraction imageView = onView(
                allOf(withId(R.id.imageView), withContentDescription("商品图示"),
                        withTagValue(is(R.drawable.bai_cai)),
                        withParent(withParent(withId(R.id.recyclerView))),
                        isDisplayed()));
        imageView.check(matchesDrawable(R.drawable.bai_cai));
    }

    // 定义一个名为matchesDrawable的公共静态方法，它接受一个名为expectedResourceId的参数，这个参数是你期望的图片的资源ID。
    public static ViewAssertion matchesDrawable(final int expectedResourceId) {
        // 这个方法返回一个ViewAssertion对象，这个对象是一个函数式接口，它接受两个参数：一个View对象和一个NoViewFoundException对象。
        return (view, noViewFoundException) -> {
            // 如果view不是ImageView的实例，那么抛出noViewFoundException。
            if (!(view instanceof ImageView)){
                throw noViewFoundException;
            }
            // 将view强制转换为ImageView对象。
            ImageView imageView = (ImageView) view;
            // 使用expectedResourceId从imageView的上下文中获取预期的Drawable对象。
            Drawable expectedDrawable = ContextCompat.getDrawable(imageView.getContext(), expectedResourceId);
            // 从预期的Drawable对象中获取Bitmap对象。
            Bitmap bitmap = ((BitmapDrawable) expectedDrawable).getBitmap();
            // 从imageView中获取实际的Drawable对象。
            Drawable actualDrawable = imageView.getDrawable();
            // 从实际的Drawable对象中获取Bitmap对象。
            Bitmap actualBitmap = ((BitmapDrawable) actualDrawable).getBitmap();
            // 使用assertTrue方法检查实际的Bitmap对象是否与预期的Bitmap对象相同。
            assertTrue(actualBitmap.sameAs(bitmap));
        };
    }
}
