package com.phoenix.soft.agenda.rxfirebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

/**
 * Created by yaoda on 21/03/17.
 */

final class SingleQueryObservable extends Single<DataSnapshot> {
    private Query query;

    SingleQueryObservable(Query query) {

        this.query = query;
    }

    @Override
    protected void subscribeActual(SingleObserver<? super DataSnapshot> observer) {
        Listener listener = new Listener(observer, query);
        query.addListenerForSingleValueEvent(listener);
        observer.onSubscribe(listener);
    }

    static final class Listener implements Disposable, ValueEventListener {
        private final SingleObserver<? super DataSnapshot> observer;
        private final Query query;
        private final AtomicBoolean unsubscribed = new AtomicBoolean();


        Listener(SingleObserver<? super DataSnapshot> observer, Query query) {

            this.observer = observer;
            this.query = query;
        }

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (!isDisposed()) {
                observer.onSuccess(dataSnapshot);
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            if (!isDisposed()) {
                observer.onError(new DatabaseException(databaseError.getMessage()));
            }
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

}
