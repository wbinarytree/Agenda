package com.phoenix.soft.agenda.rxfirebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.Query;
import com.phoenix.soft.agenda.module.firebase.FirebaseKey;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by yaoda on 21/03/17.
 */

public final class RxDatabase {
    public static <T extends FirebaseKey> Observable<T> limitToFirst(Class<T> tClass, Query query) {
        return new FirebaseObservable<>(tClass, query);
    }
    public static Observable childEvent(Query query){
        return Observable.just("");
    }

    public static Maybe<DataSnapshot> queryOnce(Query query) {
        return new SingleQueryObservable(query);
    }

}
