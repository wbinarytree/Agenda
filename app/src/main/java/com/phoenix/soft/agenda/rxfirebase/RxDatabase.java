package com.phoenix.soft.agenda.rxfirebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Observable;

/**
 * Created by yaoda on 21/03/17.
 */

public final class RxDatabase {
    public static <T extends FirebaseModule> Observable<T> limitToFirst(Class<T> tClass, Query query) {
        return new FirebaseObservable<>(tClass, query);
    }

    public static Observable childEvent(Query query) {
        return Observable.just("");
    }

    public static Maybe<DataSnapshot> queryOnce(Query query) {
        return new SingleQueryObservable(query);
    }

    public static <V extends FirebaseModule.ToFire<T>, T extends FirebaseModule<V>> List<V> fireParserList(DataSnapshot dataSnapshot, Class<T> tClass) {
        List<V> vList = new ArrayList<>();
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            T fire = snapshot.getValue(tClass);
            fire.setKey(dataSnapshot.getKey());
            vList.add(0, fire.toModule());
        }
        return vList;
    }

    public static <V extends FirebaseModule.ToFire<T>, T extends FirebaseModule<V>> V fireParser(DataSnapshot dataSnapshot, Class<T> tClass) {
        T fire = dataSnapshot.getValue(tClass);
        fire.setKey(dataSnapshot.getKey());

        return fire.toModule();
    }

}
