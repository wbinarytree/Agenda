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

import com.phoenix.soft.costy.models.Account;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import java.util.List;

/**
 * Created by yaoda on 04/04/17.
 */

public interface AccountSourceRT {
    Observable<List<Account>> getAccountList();

    Observable<ValueEvent<Account>> getAccountUpdate();

    Maybe<Account> getAccount(String id);

    Observable<Boolean> addAccount(Account account);

    Observable<Boolean> updateAccount(Account account);

    Observable<Boolean> deleteAccount(Account account);
}
