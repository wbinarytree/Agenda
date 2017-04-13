package com.phoenix.soft.costy.account;


import com.phoenix.soft.costy.models.Account;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by yaoda on 13/04/17.
 */

public interface ViewModel {
    Observable<List<Account>> getAccountList();

    Observable<Account> getAccountUpdate();
}
