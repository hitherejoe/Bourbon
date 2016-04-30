package com.hitherejoe.bourbon.common.ui.shot;

import com.hitherejoe.bourbon.common.TestDataFactory;
import com.hitherejoe.bourbon.common.data.DataManager;
import com.hitherejoe.bourbon.common.data.model.Comment;
import com.hitherejoe.bourbon.common.util.RxSchedulersOverrideRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import rx.Single;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ShotPresenterTest {

    @Mock
    ShotMvpView mMockShotMvpView;
    @Mock
    DataManager mMockDataManager;
    private ShotPresenter mShotPresenter;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() {
        mShotPresenter = new ShotPresenter(mMockDataManager);
        mShotPresenter.attachView(mMockShotMvpView);
    }

    @After
    public void detachView() {
        mShotPresenter.detachView();
    }

    @Test
    public void getCommentsSuccessful() {
        List<Comment> comments = TestDataFactory.makeComments(5);
        stubDataManagerGetComments(Single.just(comments));
        mShotPresenter.getComments(TestDataFactory.randomInt(), 0, 0);
        verify(mMockShotMvpView).showProgress();
        verify(mMockShotMvpView).showComments(comments);
        verify(mMockShotMvpView).hideProgress();
    }

    @Test
    public void getCommentsEmpty() {
        List<Comment> comments = new ArrayList<>();
        stubDataManagerGetComments(Single.just(comments));
        mShotPresenter.getComments(TestDataFactory.randomInt(), 0, 0);
        verify(mMockShotMvpView).showProgress();
        verify(mMockShotMvpView).showEmptyComments();
        verify(mMockShotMvpView).hideProgress();
    }

    @Test
    public void getCommentsFailure() {
        stubDataManagerGetComments(Single.<List<Comment>>error(new RuntimeException()));
        mShotPresenter.getComments(TestDataFactory.randomInt(), 0, 0);
        verify(mMockShotMvpView).showProgress();
        verify(mMockShotMvpView).showError();
        verify(mMockShotMvpView).hideProgress();
    }

    private void stubDataManagerGetComments(Single<List<Comment>> single) {
        when(mMockDataManager.getComments(anyInt(), anyInt(), anyInt())).thenReturn(single);
    }

}