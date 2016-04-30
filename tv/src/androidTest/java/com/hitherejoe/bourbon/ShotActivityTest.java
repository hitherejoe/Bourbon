package com.hitherejoe.bourbon;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.hitherejoe.bourbon.ui.shot.ShotActivity;
import com.hitherejoe.bourbon.common.test.TestDataFactory;
import com.hitherejoe.bourbon.common.data.model.Comment;
import com.hitherejoe.bourbon.common.data.model.Shot;
import com.hitherejoe.bourbon.common.injection.component.TestComponentRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import java.util.Collections;
import java.util.List;

import rx.Single;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
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
    public void shotViewDisplays() {
        Shot shot = TestDataFactory.makeShot(0);
        when(component.getMockDataManager().getComments(anyInt(), anyInt(), anyInt()))
                .thenReturn(Single.just(Collections.<Comment>emptyList()));
        Intent intent = ShotActivity.newStartIntent(InstrumentationRegistry.getContext(), shot);
        main.launchActivity(intent);

        onView(withId(R.id.image_shot))
                .check(matches(isDisplayed()));
        onView(withId(R.id.page_indicator))
                .check(matches(isDisplayed()));
        onView(withId(R.id.pager_shot))
                .check(matches(isDisplayed()));
    }

    @Test
    public void detailViewDisplays() {
        Shot shot = TestDataFactory.makeShot(0);
        when(component.getMockDataManager().getComments(anyInt(), anyInt(), anyInt()))
                .thenReturn(Single.just(Collections.<Comment>emptyList()));
        Intent intent = ShotActivity.newStartIntent(InstrumentationRegistry.getContext(), shot);
        main.launchActivity(intent);


        onView(withId(R.id.image_avatar))
                .check(matches(isDisplayed()));
        onView(withText(shot.title))
                .check(matches(isDisplayed()));
        onView(withText(shot.likes_count))
                .check(matches(isDisplayed()));
        onView(withText(shot.views_count))
                .check(matches(isDisplayed()));
    }

    @Test
    public void commentsDisplayAndAreBrowseable() {
        Shot shot = TestDataFactory.makeShot(0);
        List<Comment> comments = TestDataFactory.makeComments(10);
        when(component.getMockDataManager().getComments(anyInt(), anyInt(), anyInt()))
                .thenReturn(Single.just(comments));
        Intent intent = ShotActivity.newStartIntent(InstrumentationRegistry.getContext(), shot);
        main.launchActivity(intent);

        for (Comment comment : comments) {
            onView(withId(R.id.pager_shot))
                    .perform(swipeLeft());
            onView(withText(comment.user.username))
                    .check(matches(isDisplayed()));
            onView(withText(comment.body))
                    .check(matches(isDisplayed()));
        }
    }

}