package com.hitherejoe.bourboncommon.data;

import com.hitherejoe.bourboncommon.data.model.Comment;
import com.hitherejoe.bourboncommon.data.model.Shot;
import com.hitherejoe.bourboncommon.data.remote.BourbonService;
import com.hitherejoe.bourboncommon.common.TestDataFactory;
import com.hitherejoe.bourboncommon.util.RxSchedulersOverrideRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import rx.Single;
import rx.observers.TestSubscriber;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Tests for DataManager methods related to retrieving data
 */
@RunWith(MockitoJUnitRunner.class)
public class DataManagerTest {

    @Mock
    BourbonService mMockBourbonService;
    DataManager mDataManager;

    @Rule
    // Must be added to every test class that targets app code that uses RxJava
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() {
        mDataManager = new DataManager(mMockBourbonService);
    }

    @Test
    public void getShotsCompletesAndEmitsShots() {
        List<Shot> shots = TestDataFactory.makeShots(10);
        stubBourbonServiceGetShots(Single.just(shots));

        TestSubscriber<List<Shot>> testSubscriber = new TestSubscriber<>();
        mDataManager.getShots(0, 0).subscribe(testSubscriber);
        testSubscriber.assertCompleted();
        testSubscriber.assertValue(shots);
    }

    @Test
    public void getCommentsCompletesAndEmitsComments() {
        List<Comment> comments = TestDataFactory.makeComments(10);
        stubBourbonServiceGetComments(Single.just(comments));

        TestSubscriber<List<Comment>> testSubscriber = new TestSubscriber<>();
        mDataManager.getComments(TestDataFactory.randomInt(), 0, 0).subscribe(testSubscriber);
        testSubscriber.assertCompleted();
        testSubscriber.assertValue(comments);
    }

    private void stubBourbonServiceGetShots(Single<List<Shot>> single) {
        when(mMockBourbonService.getShots(anyString(), anyInt(), anyInt())).thenReturn(single);
    }

    private void stubBourbonServiceGetComments(Single<List<Comment>> single) {
        when(mMockBourbonService.getComments(anyInt(), anyString(), 0, 0)).thenReturn(single);
    }

}