package com.hitherejoe.bourbon.test;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.hitherejoe.bourbon.ui.browse.BrowseActivity;
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
import static android.support.test.espresso.matcher.ViewMatchers.withText;
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


        onView(withText(com.hitherejoe.bourbon.R.string.text_error_loading_shots))
                .check(matches(isDisplayed()));
        onView(withText(com.hitherejoe.bourbon.R.string.text_reload))
                .check(matches(isDisplayed()));
    }

    @Test
    public void emptyViewDisplaysWhenLoadingContentFails() throws InterruptedException {
        when(component.getMockDataManager().getShots(anyInt(), anyInt()))
                .thenReturn(Single.just(Collections.<Shot>emptyList()));
        main.launchActivity(null);


        onView(withText(com.hitherejoe.bourbon.R.string.text_no_shots))
                .check(matches(isDisplayed()));
        onView(withText(com.hitherejoe.bourbon.R.string.text_check_again))
                .check(matches(isDisplayed()));
    }

}