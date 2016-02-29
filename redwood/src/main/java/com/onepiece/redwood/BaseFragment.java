package com.onepiece.redwood;

import android.support.v4.app.Fragment;

/**
 * Created by Administrator on 2015/10/1.
 */
public abstract class BaseFragment extends Fragment {
    @Override
    public void onDestroy() {
        super.onDestroy();
       /* RefWatcher refWatcher = BaseApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);*/
    }
}
