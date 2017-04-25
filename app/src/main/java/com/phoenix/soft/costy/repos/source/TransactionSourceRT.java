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

package com.phoenix.soft.costy.repos.source;

import com.phoenix.soft.costy.models.Transaction;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import java.util.List;

/**
 * Created by yaoda on 05/04/17.
 */

public interface TransactionSourceRT {
    Observable<List<Transaction>> getTransactionList();

    Observable<ValueEvent<Transaction>> getTransactionUpdate();

    Maybe<Transaction> getTransaction(String id);

    Observable<Boolean> addTransaction(Transaction transaction);

    Observable<Boolean> updateTransaction(Transaction transaction);

    Observable<Boolean> deleteTransaction(Transaction transaction);
}
