package com.phoenix.soft.costy;

/**
 * Created by yaoda on 22/03/17.
 */

public interface BaseContract {
    public interface BaseView{

    }
    public interface BasePresenter<T extends BaseView>{
        void attachView(T view);
        void detachView();
    }
}
