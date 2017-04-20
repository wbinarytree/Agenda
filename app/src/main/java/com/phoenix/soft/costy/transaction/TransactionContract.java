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

package com.phoenix.soft.costy.transaction;

import com.phoenix.soft.costy.base.BaseContract;
import com.phoenix.soft.costy.models.Transaction;

import java.util.List;

/**
 * Created by yaoda on 23/02/17.
 */

public interface TransactionContract {
    interface View extends BaseContract.BaseView {
        void showTransactionList();
        void showAddDetailDialog();
        void updateList();
        void showNoTransaction();
        void showError(String errorMessage);
        void initTransactionList(List<Transaction> transactions);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void loadDetailList();

        void addDetail(Transaction transaction);
    }
}
