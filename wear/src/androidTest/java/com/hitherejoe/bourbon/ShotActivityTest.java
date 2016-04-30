package com.hitherejoe.bourbon;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.hitherejoe.bourbon.ui.browse.BrowseActivity;
import com.hitherejoe.bourbon.ui.comment.CommentActivity;
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
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeUp;
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
    public final ActivityTestRule<CommentActivity> main =
            new ActivityTestRule<>(CommentActivity.class, false, false);

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
        Intent intent = CommentActivity.newIntent(InstrumentationRegistry.getContext(), shot);
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

}