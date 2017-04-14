/*
 * Copyright 2017 WBinaryTree
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
