package com.hitherejoe.bourbon.test;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.hitherejoe.bourbon.R;
import com.hitherejoe.bourbon.ui.browse.BrowseActivity;
import com.hitherejoe.bourboncorecommon.data.model.Comment;
import com.hitherejoe.bourboncorecommon.data.model.Shot;
import com.hitherejoe.bourboncorecommon.injection.component.TestComponentRule;
import com.hitherejoe.bourboncorecommon.util.TestDataFactory;

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
import static org.hamcrest.Matchers.not;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class BrowseActivityTest {

    public final TestComponentRule component =
            new TestComponentRule(InstrumentationRegistry.getTargetContext());
    public final ActivityTestRule<BrowseActivity> main =
            new ActivityTestRule<>(BrowseActivity.class, false, false);

    // TestComponentRule needs to go first to make sure the Dagger ApplicationTestComponent is set
    // in the Application before any Activity is launched.
    @Rule
    public TestRule chain = RuleChain.outerRule(component).around(main);

    @Test
    public void errorViewDisplaysWhenLoadingContentFails() throws InterruptedException {
        when(component.getMockDataManager().getShots(anyInt(), anyInt()))
                .thenReturn(Single.<List<Shot>>error(new RuntimeException()));
        main.launchActivity(null);


        onView(withText(R.string.text_error_loading_shots))
                .check(matches(isDisplayed()));
        onView(withText(R.string.text_reload))
                .check(matches(isDisplayed()));
    }

    @Test
    public void emptyViewDisplaysWhenLoadingContentFails() throws InterruptedException {
        when(component.getMockDataManager().getShots(anyInt(), anyInt()))
                .thenReturn(Single.just(Collections.<Shot>emptyList()));
        main.launchActivity(null);


        onView(withText(R.string.text_no_shots))
                .check(matches(isDisplayed()));
        onView(withText(R.string.text_reload))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shotItemViewsDisplayWhenClickingReload() {
        when(component.getMockDataManager().getShots(anyInt(), anyInt()))
                .thenReturn(Single.<List<Shot>>error(new RuntimeException()));
        main.launchActivity(null);

        Shot shot = TestDataFactory.makeShot();
        when(component.getMockDataManager().getShots(anyInt(), anyInt()))
                .thenReturn(Single.just(Collections.singletonList(shot)));
        onView(withText(R.string.text_reload))
                .perform(click());

        onView(withText(R.string.text_reload))
                .check(matches(not(isDisplayed())));
        checkShotDetailsDisplay(shot);
    }

    @Test
    public void shotItemViewsDisplayWhenClickingCheckAgain() {
        when(component.getMockDataManager().getShots(anyInt(), anyInt()))
                .thenReturn(Single.just(Collections.<Shot>emptyList()));
        main.launchActivity(null);

        Shot shot = TestDataFactory.makeShot();
        when(component.getMockDataManager().getShots(anyInt(), anyInt()))
                .thenReturn(Single.just(Collections.singletonList(shot)));
        onView(withText(R.string.text_reload))
                .perform(click());

        onView(withText(R.string.text_reload))
                .check(matches(not(isDisplayed())));
        checkShotDetailsDisplay(shot);
    }

    @Test
    public void shotsDisplayAndAreBrowsable() throws InterruptedException {
        List<Shot> shots = TestDataFactory.makeShots(5);
        when(component.getMockDataManager().getShots(anyInt(), anyInt()))
                .thenReturn(Single.just(shots));
        main.launchActivity(null);

        for (Shot shot : shots) {
            checkShotDetailsDisplay(shot);

            onView(withId(R.id.pager_shots))
                    .perform(swipeUp());
        }
    }

    @Test
    public void clickingShotNavigatesToShotActivity() throws InterruptedException {
        Shot shot = TestDataFactory.makeShot();
        when(component.getMockDataManager().getShots(anyInt(), anyInt()))
                .thenReturn(Single.just(Collections.singletonList(shot)));
        main.launchActivity(null);

        when(component.getMockDataManager().getComments(anyInt(), anyInt(), anyInt()))
                .thenReturn(Single.just(TestDataFactory.makeComments(5)));

        onView(withId(R.id.image_shot))
                .perform(click());

        onView(withId(R.id.pager_comments))
                .check(matches(isDisplayed()));
    }

    private void checkShotDetailsDisplay(Shot shot) {
        onView(withId(R.id.pager_shots))
                .perform(swipeLeft());

        onView(withText(shot.title))
                .check(matches(isDisplayed()));
        onView(withText(shot.user.username))
                .check(matches(isDisplayed()));

        onView(withId(R.id.pager_shots))
                .perform(swipeLeft());

        onView(withText(shot.likes_count))
                .check(matches(isDisplayed()));

        onView(withId(R.id.pager_shots))
                .perform(swipeLeft());

        onView(withText(shot.views_count))
                .check(matches(isDisplayed()));
    }

}