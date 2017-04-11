package com.phoenix.soft.costy.rxfirebase;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;

import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by yaoda on 21/03/17.
 */

final class ChildEventObservable<T> extends Observable<T> {
    @Override
    protected void subscribeActual(Observer<? super T> observer) {

    }

    static final class EventListener implements ChildEventListener, Disposable {

        private final Observer observer;
        private final Query query;
        private final AtomicBoolean unsubscribed = new AtomicBoolean();

        EventListener(Observer observer, Query query) {
            this.observer = observer;
            this.query = query;

        }

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            observer.onNext(dataSnapshot);
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }

        @Override
        public void dispose() {
            unsubscribed.compareAndSet(false, true);
            query.removeEventListener(this);
        }

        @Override
        public boolean isDisposed() {
            return unsubscribed.get();
        }
    }

    ;
}
