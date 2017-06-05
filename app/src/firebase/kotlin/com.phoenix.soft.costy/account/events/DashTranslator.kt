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

package com.phoenix.soft.costy.account.events

import com.phoenix.soft.costy.account.events.DashEvent.AccountEvent
import com.phoenix.soft.costy.account.events.DashEvent.Companion.Type.ADD
import com.phoenix.soft.costy.account.events.DashEvent.Companion.Type.DELETE
import com.phoenix.soft.costy.account.events.DashEvent.Companion.Type.UPDATE
import com.phoenix.soft.costy.account.events.DashEvent.InitEvent
import com.phoenix.soft.costy.account.events.DashEvent.UpdateListEvent
import com.phoenix.soft.costy.account.events.DashUiModel.Companion.ErrorType.NO_ACCOUNT
import com.phoenix.soft.costy.repos.source.AccountSourceRT
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import javax.inject.Inject


/**
 * Created by yaoda on 05/06/17.
 */
class DashTranslator @Inject constructor(val repo: AccountSourceRT) {
    val middle: Subject<DashEvent> = PublishSubject.create<DashEvent>()
    val res: Observable<DashUiModel> = middle.publish {
        Observable.merge(it.ofType(AccountEvent::class.java).accountProcess(),
            it.ofType(UpdateListEvent::class.java).updateProcess(),
            it.ofType(InitEvent::class.java).initProcess())
    }
        .distinctUntilChanged()
        .replay(1)
        .autoConnect()

    private fun Observable<InitEvent>.initProcess(): Observable<DashUiModel> =
        this.flatMap {
            repo.accountList
                .map {
                    if (it.isEmpty())
                        DashUiModel.error("No Account", NO_ACCOUNT)
                    else
                        DashUiModel(accounts = it)
                }
                .startWith(DashUiModel.process())
                .onErrorReturn { DashUiModel.error(it.message) }
        }


    private fun Observable<UpdateListEvent>.updateProcess(): Observable<DashUiModel> =
        this.map { InitEvent() }.initProcess()


    private fun Observable<AccountEvent>.accountProcess(): Observable<DashUiModel> =
        this.flatMap { (account, type) ->
            val observable = when (type) {
                ADD -> repo.addAccount(account)
                DELETE -> repo.deleteAccount(account)
                UPDATE -> repo.updateAccount(account)
            }
            observable.map { DashUiModel() }
                .startWith(DashUiModel.process())
                .onErrorReturn { DashUiModel.error(it.message) }
        }


    fun load(event: Observable<DashEvent>): Observable<DashUiModel> {
        event.subscribe(middle)
        return res
    }

}


fun Observable<DashEvent>.load(trans: DashTranslator): Observable<DashUiModel> =
    trans.load(this)

