package com.hitherejoe.androidtestcommon.helper;

import com.hitherejoe.bourboncorecommon.data.model.Comment;
import com.hitherejoe.bourboncorecommon.data.model.Shot;
import com.hitherejoe.bourboncorecommon.injection.component.TestComponentRule;

import java.util.List;

import rx.Single;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

public class StubHelper {

    public static void stubDataManagerGetShots(TestComponentRule component,
                                               Single<List<Shot>> single) {
        when(component.getMockDataManager().getShots(anyInt(), anyInt()))
                .thenReturn(single);
    }

    public static void stubDataManagerGetComments(TestComponentRule component,
                                                  Single<List<Comment>> single) {
        when(component.getMockDataManager().getComments(anyInt(), anyInt(), anyInt()))
                .thenReturn(single);
    }
}
