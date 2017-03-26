package com.phoenix.soft.agenda.rxfirebase;

/**
 * Created by yaoda on 20/03/17.
 */

public interface FirebaseModule<T extends FirebaseModule.ToFire> {
    String getKey();
    void setKey(String key);
    T toModule();
    interface ToFire<T extends FirebaseModule>{
        T toFire();
    }
}
