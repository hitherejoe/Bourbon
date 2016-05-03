package com.hitherejoe.bourboncommon.common.ui.browse;

import com.hitherejoe.bourboncommon.common.data.DataManager;
import com.hitherejoe.bourboncommon.common.data.model.Shot;
import com.hitherejoe.bourboncommon.util.RxSchedulersOverrideRule;
import com.hitherejoe.bourboncommon.util.TestDataFactory;

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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BrowsePresenterTest {

    @Mock
    BrowseMvpView mMockBrowseMvpView;
    @Mock
    DataManager mMockDataManager;
    private BrowsePresenter mBrowsePresenter;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() {
        mBrowsePresenter = new BrowsePresenter(mMockDataManager);
        mBrowsePresenter.attachView(mMockBrowseMvpView);
    }

    @After
    public void detachView() {
        mBrowsePresenter.detachView();
    }

    @Test
    public void getShotsSuccessful() {
        List<Shot> shots = TestDataFactory.makeShots(5);
        stubDataManagerGetShots(Single.just(shots));
        mBrowsePresenter.getShots(0, 0);
        verify(mMockBrowseMvpView).showProgress();
        verify(mMockBrowseMvpView).showShots(shots);
        verify(mMockBrowseMvpView).hideProgress();
    }

    @Test
    public void getShotsEmpty() {
        List<Shot> shots = new ArrayList<>();
        stubDataManagerGetShots(Single.just(shots));
        mBrowsePresenter.getShots(0, 0);
        verify(mMockBrowseMvpView).showProgress();
        verify(mMockBrowseMvpView).showEmpty();
        verify(mMockBrowseMvpView).hideProgress();
    }

    @Test
    public void getShotsFailure() {
        stubDataManagerGetShots(Single.<List<Shot>>error(new RuntimeException()));
        mBrowsePresenter.getShots(0, 0);
        verify(mMockBrowseMvpView).showProgress();
        verify(mMockBrowseMvpView).showError();
        verify(mMockBrowseMvpView).hideProgress();
    }

    private void stubDataManagerGetShots(Single<List<Shot>> single) {
        when(mMockDataManager.getShots(0, 0)).thenReturn(single);
    }

}