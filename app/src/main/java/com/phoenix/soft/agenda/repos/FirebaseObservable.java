package com.phoenix.soft.agenda.repos;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.phoenix.soft.agenda.module.firebase.FirebaseKey;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by yaoda on 20/03/17.
 * FirebaseObservable(Account.Class,)
 */


public class FirebaseObservable<T extends FirebaseKey> extends Observable<T> {

    DatabaseReference dbRef;
    private Listener listener;
    private Class<T> tClass;
    private int limit;
    private String child;

    public FirebaseObservable() {

    }

    public FirebaseObservable(Class<T> tClass, int number, String child,DatabaseReference dbRef) {
        this.tClass = tClass;
        this.limit = number;
        this.child = child;
        this.dbRef = dbRef;
    }

    @Override
    protected void subscribeActual(Observer<? super T> observer) {
        Listener listener = new Listener(observer);
        dbRef.child(child).limitToFirst(limit).addListenerForSingleValueEvent(listener);
        observer.onSubscribe(listener);
    }

    private class Listener implements Disposable, ValueEventListener {
        Observer<? super T> observer;

        public Listener(Observer<? super T> observer) {
            this.observer = observer;
        }

        @Override
        public void dispose() {
            dbRef.removeEventListener(this);
        }

        @Override
        public boolean isDisposed() {
            return false;
        }


        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            try {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    T value = data.getValue(tClass);
                    value.setKey(data.getKey());
                    observer.onNext(value);
                }
                observer.onComplete();
            } catch (Exception e) {
                observer.onError(e);
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            observer.onError(new Exception(databaseError.getMessage()));
        }
    }

}
