package com.phoenix.soft.agenda.detail;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.phoenix.soft.agenda.MainApplication;
import com.phoenix.soft.agenda.module.Account;
import com.phoenix.soft.agenda.module.Detail;
import com.phoenix.soft.agenda.module.firebase.DetailFire;
import com.phoenix.soft.agenda.repos.FirebaseObservable;

import org.joda.money.Money;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by yaoda on 23/02/17.
 */
public class DetailPresenter implements DetailContract.Presenter {

    private static final String TAG = "DetailPresenter";
    private final List<Detail> details;
    private final DetailContract.View view;
    @Inject
    @Named("Account")
    DatabaseReference dbRef;
    private Account account;
    private String key;

    public DetailPresenter(Account account, DetailContract.View view) {
        this.account = account;
        this.details = account.getDetailList();
        this.view = view;
    }

    public DetailPresenter(String key, DetailContract.View view) {
        MainApplication.getFirebaseComponent().inject(this);
        this.key = key;
        details = new ArrayList<>();
        this.view = view;
    }

    @Override
    public void loadDetailList() {

        FirebaseObservable<DetailFire> observable = new FirebaseObservable<>(DetailFire.class, 10, "detail/" + key, dbRef);
        observable.subscribe(new DisposableObserver<DetailFire>() {
            @Override
            public void onNext(DetailFire detailFire) {
                try {
                    details.add(detailFire.toDetail());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "onNext: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: ");
                view.showError("Error");
            }

            @Override
            public void onComplete() {
//                if (details.isEmpty()) {
//                    view.showNoDetail();
//                } else {
//                }
                view.showDetailList(details);
            }
        });
    }

    @Override
    public void addDetail(String number, boolean add) {
        // TODO: 03/03/17 currency selector
        Money money = add ? Money.parse("USD " + number) : Money.parse("USD " + "-" + number);
        Detail detail = new Detail();
        detail.setMoney(money);
        detail.setDate(new Date());
        detail.setDesc("temp desc");
        details.add(0, detail);
        dbRef.child("detail")
             .child(key)
             .push()
             .setValue(detail.toDetailFire())
             .addOnSuccessListener(aVoid -> Log.d(TAG, "onSuccess: "));
        view.updateList();
    }
}
