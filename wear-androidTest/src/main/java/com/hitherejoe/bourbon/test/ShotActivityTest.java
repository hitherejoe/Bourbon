package com.hitherejoe.bourbon.test;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.hitherejoe.bourbon.R;
import com.hitherejoe.bourbon.ui.shot.ShotActivity;
import com.hitherejoe.bourboncommon.common.data.model.Comment;
import com.hitherejoe.bourboncommon.common.data.model.Shot;
import com.hitherejoe.bourboncommon.common.injection.component.TestComponentRule;
import com.hitherejoe.bourboncommon.util.TestDataFactory;

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
    public void commentsDisplayAndAreBrowsable() throws InterruptedException {
        Shot shot = TestDataFactory.makeShot(0);
        List<Comment> comments = TestDataFactory.makeComments(5);
        when(component.getMockDataManager().getComments(anyInt(), anyInt(), anyInt()))
                .thenReturn(Single.just(comments));
        Intent intent = ShotActivity.newIntent(InstrumentationRegistry.getContext(), shot);
        main.launchActivity(intent);

        for (Comment comment : comments) {
            onView(withText(comment.user.username))
                    .check(matches(isDisplayed()));
            onView(withText(comment.body))
                    .check(matches(isDisplayed()));

            onView(withId(R.id.pager_comments))
                    .perform(swipeLeft());
        }
    }

    @Test
    public void errorViewDisplaysWhenLoadingContentFails() {
        Shot shot = TestDataFactory.makeShot(0);
        when(component.getMockDataManager().getComments(anyInt(), anyInt(), anyInt()))
                .thenReturn(Single.<List<Comment>>error(new RuntimeException()));
        Intent intent = ShotActivity.newIntent(InstrumentationRegistry.getContext(), shot);
        main.launchActivity(intent);

        onView(withText(R.string.text_error_loading_comments))
                .check(matches(isDisplayed()));
        onView(withText(R.string.text_reload))
                .check(matches(isDisplayed()));
    }

    @Test
    public void emptyViewDisplaysWhenNoCommentsReturned() {
        Shot shot = TestDataFactory.makeShot(0);
        when(component.getMockDataManager().getComments(anyInt(), anyInt(), anyInt()))
                .thenReturn(Single.just(Collections.<Comment>emptyList()));
        Intent intent = ShotActivity.newIntent(InstrumentationRegistry.getContext(), shot);
        main.launchActivity(intent);

        onView(withText(R.string.text_no_recent_comments))
                .check(matches(isDisplayed()));
        onView(withText(R.string.text_check_again))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shotItemViewsDisplayWhenClickingReload() {
        Shot shot = TestDataFactory.makeShot(0);
        when(component.getMockDataManager().getComments(anyInt(), anyInt(), anyInt()))
                .thenReturn(Single.<List<Comment>>error(new RuntimeException()));
        Intent intent = ShotActivity.newIntent(InstrumentationRegistry.getContext(), shot);
        main.launchActivity(intent);

        Comment comment = TestDataFactory.makeComment(5);
        when(component.getMockDataManager().getComments(anyInt(), anyInt(), anyInt()))
                .thenReturn(Single.just(Collections.singletonList(comment)));
        onView(withId(R.id.text_message_action))
                .perform(click());

        onView(withText(comment.user.username))
                .check(matches(isDisplayed()));
        onView(withText(comment.body))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shotItemViewsDisplayWhenClickingCheckAgain() throws InterruptedException {
        Shot shot = TestDataFactory.makeShot(0);
        when(component.getMockDataManager().getComments(anyInt(), anyInt(), anyInt()))
                .thenReturn(Single.just(Collections.<Comment>emptyList()));
        Intent intent = ShotActivity.newIntent(InstrumentationRegistry.getContext(), shot);
        main.launchActivity(intent);

        Comment comment = TestDataFactory.makeComment(5);
        when(component.getMockDataManager().getComments(anyInt(), anyInt(), anyInt()))
                .thenReturn(Single.just(Collections.singletonList(comment)));
        onView(withId(R.id.text_message_action))
                .perform(click());

        onView(withText(comment.user.username))
                .check(matches(isDisplayed()));
        onView(withText(comment.body))
                .check(matches(isDisplayed()));
    }

}