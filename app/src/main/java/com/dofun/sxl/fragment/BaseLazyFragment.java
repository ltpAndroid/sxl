package com.dofun.sxl.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * 描述：懒加载的Fragment父类 作者：tyc
 */
public abstract class BaseLazyFragment extends Fragment implements
        FragmentUserVisibleController.UserVisibleCallback {
    private FragmentUserVisibleController userVisibleController;
    /**
     * Fragment是否第一次可见
     */
    private boolean isFirstVisible;

    public BaseLazyFragment() {
        userVisibleController = new FragmentUserVisibleController(this, this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onViewCreated(view, savedInstanceState);
        isFirstVisible = true;
        onVisible();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        // TODO Auto-generated method stub
        super.setUserVisibleHint(isVisibleToUser);
        userVisibleController.setUserVisibleHint(isVisibleToUser);
        // if(getUserVisibleHint() && getView() != null && isAdded()){
        // //Fragment可见时
        // onVisible();
        // }else if(!getUserVisibleHint() && getView() != null){ //Fragment不可见时
        // onInvisible();
        // }
    }

    /**
     * 子类需要实现懒加载时，重写改方法
     *
     * @param view 懒加载
     */
    protected abstract void onLazyLoad(View view);

    private void onVisible() {
        // TODO Auto-generated method stub
        if (getUserVisibleHint() && getView() != null && isAdded()) {
            if (isFirstVisible) {
                onLazyLoad(getView());
            } else {
                lazyLoadAfter(getView());
            }
            isFirstVisible = false;
        }
    }

    protected void onInvisible() {
    }

    /**
     * setUserVisibleHint(boolean) 第一次回调时, 调用方法onLazyLoad().
     * 此后每次回调setUserVisibleHint(boolean)后, 都直接回调此方法. note: 主界面，账号切换后需要重新加载数据
     */
    protected void lazyLoadAfter(View view) {
    }

    /************************************************************/
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        userVisibleController.activityCreated();
    }

    @Override
    public void onResume() {
        super.onResume();
        userVisibleController.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        userVisibleController.pause();
    }

    @Override
    public void setWaitingShowToUser(boolean waitingShowToUser) {
        userVisibleController.setWaitingShowToUser(waitingShowToUser);
    }

    @Override
    public boolean isWaitingShowToUser() {
        return userVisibleController.isWaitingShowToUser();
    }

    @Override
    public boolean isVisibleToUser() {
        return userVisibleController.isVisibleToUser();
    }

    @Override
    public void callSuperSetUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onVisibleToUserChanged(boolean isVisibleToUser,
                                       boolean invokeInResumeOrPause) {
        if (isVisibleToUser) { // 可见
            onVisible();
        } else {
            onInvisible();
        }
    }

}
