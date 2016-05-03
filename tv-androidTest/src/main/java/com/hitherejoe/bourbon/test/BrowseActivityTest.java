package com.hitherejoe.bourbon.test;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.hitherejoe.bourbon.ui.browse.BrowseActivity;
import com.hitherejoe.bourboncommon.common.data.model.Comment;
import com.hitherejoe.bourboncommon.common.data.model.Shot;
import com.hitherejoe.bourboncommon.common.injection.component.TestComponentRule;
import com.hitherejoe.androidtestcommon.helper.StubHelper;
import com.hitherejoe.androidtestcommon.common.TestDataFactory;

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
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.hitherejoe.androidtestcommon.util.CustomMatchers.withItemText;
import static org.hamcrest.core.IsNot.not;

import com.hitherejoe.bourbon.R;

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
        StubHelper.stubDataManagerGetShots(component,
                Single.<List<Shot>>error(new RuntimeException()));
        main.launchActivity(null);

        onView(withText(R.string.title_oops))
                .check(matches(isDisplayed()));
        onView(withText(R.string.text_error_loading_shots))
                .check(matches(isDisplayed()));
        onView(withText(R.string.text_reload))
                .check(matches(isDisplayed()));
    }

    @Test
    public void emptyViewDisplaysWhenLoadingContentFails() throws InterruptedException {
        StubHelper.stubDataManagerGetShots(component, Single.just(Collections.<Shot>emptyList()));
        main.launchActivity(null);

        onView(withText(R.string.title_no_shots))
                .check(matches(isDisplayed()));
        onView(withText(R.string.text_no_shots))
                .check(matches(isDisplayed()));
        onView(withText(R.string.text_check_again))
                .check(matches(isDisplayed()));
    }

    @Test
    public void clickingReloadAfterError() {
        List<Shot> shots = TestDataFactory.makeShots(3);
        StubHelper.stubDataManagerGetShots(component,
                Single.<List<Shot>>error(new RuntimeException()));
        main.launchActivity(null);

        StubHelper.stubDataManagerGetShots(component, Single.just(shots));
        onView(withId(com.hitherejoe.bourbon.R.id.button_message))
                .perform(click());

        checkPostsDisplayOnRecyclerView(shots, 0);
    }

    @Test
    public void clickingCheckAgainAfterEmptyContent() {
        List<Shot> shots = TestDataFactory.makeShots(3);
        StubHelper.stubDataManagerGetShots(component, Single.just(Collections.<Shot>emptyList()));
        main.launchActivity(null);

        StubHelper.stubDataManagerGetShots(component, Single.just(shots));
        onView(withId(com.hitherejoe.bourbon.R.id.button_message))
                .perform(click());

        checkPostsDisplayOnRecyclerView(shots, 0);
    }

    @Test
    public void shotItemViewsDisplayWhenClickingReload() {
        List<Shot> shots = TestDataFactory.makeShots(3);
        StubHelper.stubDataManagerGetShots(component,
                Single.<List<Shot>>error(new RuntimeException()));
        main.launchActivity(null);

        StubHelper.stubDataManagerGetShots(component, Single.just(shots));
        onView(withId(com.hitherejoe.bourbon.R.id.button_message))
                .perform(click());

        onView(withId(com.hitherejoe.bourbon.R.id.button_message))
                .check(doesNotExist());
        onView(withId(com.hitherejoe.bourbon.R.id.text_message))
                .check(doesNotExist());
        onView(withId(com.hitherejoe.bourbon.R.id.image_message))
                .check(doesNotExist());
        checkPostsDisplayOnRecyclerView(shots, 0);
    }

    @Test
    public void shotItemViewsDisplayWhenClickingCheckAgain() {
        List<Shot> shots = TestDataFactory.makeShots(3);
        StubHelper.stubDataManagerGetShots(component, Single.just(Collections.<Shot>emptyList()));
        main.launchActivity(null);

        StubHelper.stubDataManagerGetShots(component, Single.just(shots));
        onView(withId(com.hitherejoe.bourbon.R.id.button_message))
                .perform(click());

        onView(withId(com.hitherejoe.bourbon.R.id.button_message))
                .check(doesNotExist());
        onView(withId(com.hitherejoe.bourbon.R.id.text_message))
                .check(doesNotExist());
        onView(withId(com.hitherejoe.bourbon.R.id.image_message))
                .check(doesNotExist());
        checkPostsDisplayOnRecyclerView(shots, 0);
    }

    @Test
    public void clickingShotNavigatesToShotActivity() {
        List<Shot> shots = TestDataFactory.makeShots(20);
        Shot shot = shots.get(0);
        StubHelper.stubDataManagerGetShots(component, Single.just(shots));
        StubHelper.stubDataManagerGetComments(component,
                Single.just(Collections.<Comment>emptyList()));
        main.launchActivity(null);

        onView(withText(shot.title))
                .perform(click());
        onView(withId(com.hitherejoe.bourbon.R.id.pager_shot))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shotItemViewsDisplayAndAreBrowseable() {
        List<Shot> shots = TestDataFactory.makeShots(10);
        StubHelper.stubDataManagerGetShots(component, Single.just(shots));
        main.launchActivity(null);

        checkPostsDisplayOnRecyclerView(shots, 0);
    }

    /**
     * This method checks that the given list of posts display within the VerticalGridView. At the time
     * of writing this, RecyclerViewActions is really buggy with grid based recycler views - so this
     * method traverses through the rows of the grid (starting at left-to-right, then right-to-left
     * and vice versa). This isn't ideal, but currently proper testing doesn't seem supported.
     *
     * @param shotsToCheck posts to be checked
     */
    private void checkPostsDisplayOnRecyclerView(List<Shot> shotsToCheck, int position) {
        int columnCount = 5;
        int size = shotsToCheck.size() + position;
        int pos = 0;

        for (int i = position; i < size; i++) {
            // The first item starts as selected, clicking on this would open the Playback Activity
            checkItemAtPosition(i, shotsToCheck.get(pos));

            // If we get to the end of the current row then we need to go down to the next one
            if (((i + 1) % columnCount) == 0) {
                int nextRowStart = i + columnCount;
                int nextRowEnd = nextRowStart - columnCount + 1;
                for (int n = nextRowStart; n >= nextRowEnd; n--) {
                    checkItemAtPosition(n, shotsToCheck.get(n - position));
                }
                // Set i to the start of the row beneath the one we've just checked
                i = i + columnCount;
            }
            pos++;
        }
    }

    private void checkItemAtPosition(int position, Shot shot) {
        if (position > 0) {
            onView(withId(com.hitherejoe.bourbon.R.id.browse_grid))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(position, click()));
        }
        onView(withItemText(shot.title, com.hitherejoe.bourbon.R.id.browse_grid)).check(matches(isDisplayed()));
        onView(withItemText(shot.description, com.hitherejoe.bourbon.R.id.browse_grid)).check(matches(isDisplayed()));
    }

}