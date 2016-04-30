package com.hitherejoe.bourbon;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.hitherejoe.bourbon.ui.shot.ShotActivity;
import com.hitherejoe.bourboncommon.common.TestDataFactory;
import com.hitherejoe.bourboncommon.data.model.Comment;
import com.hitherejoe.bourboncommon.data.model.Shot;
import com.hitherejoe.bourboncommon.injection.component.TestComponentRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import java.util.Collections;
import java.util.List;

import rx.Single;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class ShotActivityTest {

    public final TestComponentRule component =
            new TestComponentRule(InstrumentationRegistry.getTargetContext());
    public final ActivityTestRule<ShotActivity> main =
            new ActivityTestRule<>(ShotActivity.class, false, false);

    // TestComponentRule needs to go first to make sure the Dagger ApplicationTestComponent is set
    // in the Application before any Activity is launched.
    @Rule
    public TestRule chain = RuleChain.outerRule(component).around(main);

    @Test
    public void showViewsDisplay() {
        Shot shot = TestDataFactory.makeShot(0);
        when(component.getMockDataManager().getComments(anyInt(), anyInt(), anyInt()))
                .thenReturn(Single.just(Collections.<Comment>emptyList()));
        Intent intent = ShotActivity.getStartIntent(
                InstrumentationRegistry.getTargetContext(), shot);
        main.launchActivity(intent);

        onView(withId(R.id.image_shot))
                .check(matches(isDisplayed()));
        onView(withText(shot.title))
                .check(matches(isDisplayed()));
        onView(withText(shot.likes_count))
                .check(matches(isDisplayed()));
    }

    @Test
    public void commentsDisplay() {
        Shot shot = TestDataFactory.makeShot(0);
        List<Comment> comments = TestDataFactory.makeComments(1);
        when(component.getMockDataManager().getComments(anyInt(), anyInt(), anyInt()))
                .thenReturn(Single.just(comments));
        Intent intent = ShotActivity.getStartIntent(
                InstrumentationRegistry.getTargetContext(), shot);
        main.launchActivity(intent);

        for (int i = 0; i < comments.size(); i++) {
            onView(withId(R.id.recycler_comments))
                    .perform(RecyclerViewActions.scrollToPosition(i));
            onView(withText(comments.get(i).body))
                    .check(matches(isDisplayed()));
        }

        onView(withText(R.string.text_recent_comments))
                .check(matches(isDisplayed()));
    }

    @Test
    public void errorMessageDisplays() {
        Shot shot = TestDataFactory.makeShot(0);
        when(component.getMockDataManager().getComments(anyInt(), anyInt(), anyInt()))
                .thenReturn(Single.<List<Comment>>error(new RuntimeException()));
        Intent intent = ShotActivity.getStartIntent(
                InstrumentationRegistry.getTargetContext(), shot);
        main.launchActivity(intent);

        onView(withText(R.string.text_error_loading_comments))
                .check(matches(isDisplayed()));
        onView(withText(R.string.text_recent_comments))
                .check(matches(not(isDisplayed())));
    }

    @Test
    public void noComments() {
        Shot shot = TestDataFactory.makeShot(0);
        when(component.getMockDataManager().getComments(anyInt(), anyInt(), anyInt()))
                .thenReturn(Single.just(Collections.<Comment>emptyList()));
        Intent intent = ShotActivity.getStartIntent(
                InstrumentationRegistry.getTargetContext(), shot);
        main.launchActivity(intent);

        onView(withText(R.string.text_no_recent_comments))
                .check(matches(isDisplayed()));
    }

}