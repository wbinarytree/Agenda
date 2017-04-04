package com.phoenix.soft.agenda.repos;

import com.phoenix.soft.agenda.rxfirebase.FirebaseModule;

import java.util.List;

import io.reactivex.Maybe;

/**
 * Created by awang on 2017/3/26.
 */

public interface RxSourceFirebase<T extends FirebaseModule.ToFire> {
    Maybe<T> getValue(String key);

    Maybe<List<T>> getValueList();

    Maybe<List<T>> getValueListFrom(String key,int num);

    boolean addValue(T value);

    boolean deleteValue(T value);

    boolean updateValue(T value);

    void refreshTransaction();
}
