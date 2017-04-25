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

package com.phoenix.soft.costy.account;

import com.phoenix.soft.costy.base.BaseContract;
import com.phoenix.soft.costy.models.Account;
import com.phoenix.soft.costy.models.Transaction;
import com.phoenix.soft.costy.repos.source.EventType;
import java.util.List;

/**
 * Created by yaoda on 22/02/17.
 */

public interface AccountContract {
    interface View extends BaseContract.BaseView {
        void showAccountList();

        void showNoAccount();

        void showError();

        void update();

        void updateAccount(Account account, EventType updateType);

        void showModifyAccount();

        void showLoading();

        void hideLoading();

        void initAccountList(List<Account> accounts);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void loadAccount();

        void addAccount(Account account);

        void modifyAccount();

        void deleteAccount(Account account);

        void updateTransactionToAccount(Transaction transaction, Account account);
    }
}
