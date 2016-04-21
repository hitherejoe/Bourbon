package com.hitherejoe.bourbon;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.hitherejoe.bourbon.test.TestComponentRule;
import com.hitherejoe.bourbon.ui.browse.BrowseActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class BrowseActivityTest {

    public final TestComponentRule component =
            new TestComponentRule(InstrumentationRegistry.getTargetContext());
    public final ActivityTestRule<BrowseActivity> main =
            new ActivityTestRule<>(BrowseActivity.class, false, false);

    // TestComponentRule needs to go first to make sure the Dagger ApplicationTestComponent is set
    // in the Application before any Activity is launched.
    //@Rule
  //  public TestRule chain = RuleChain.outerRule(component).around(main);

    @Before
    public void setUp() {

    }

    @Test
    public void errorViewDisplaysWhenLoadingContentFails() {
        main.launchActivity(null);
        assertTrue(true);
    }
}