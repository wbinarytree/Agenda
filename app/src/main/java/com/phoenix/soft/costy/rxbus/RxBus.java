package com.phoenix.soft.costy.rxbus;

import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;

import io.reactivex.Observable;

/**
 * Created by yaoda on 03/03/17.
 */

public final class RxBus {

    private static RxBus mRxbus;
    private final Relay<Object> bus = PublishRelay.create().toSerialized();

    private RxBus() {

    }

    public static RxBus getInstance() {
        if (mRxbus == null) {
            mRxbus = new RxBus();
        }
        return mRxbus;
    }

    public void send(Object event) {
        bus.accept(event);
    }

    public Observable<Object> toObservable() {
        return bus;
    }

    public boolean hasObservers() {
        return bus.hasObservers();
    }
}
