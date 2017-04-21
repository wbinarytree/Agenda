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

package com.phoenix.soft.costy.login

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.phoenix.soft.costy.login.di.AuthScope
import com.phoenix.soft.costy.models.User
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import phoenixlib.io.rxfirebase.auth.RxAuth
import phoenixlib.io.rxfirebase.auth.RxUser
import javax.inject.Inject

/**
 * Created by phoenix on 2017/4/16.
 */
@AuthScope
class FirebaseAuthManager @Inject constructor(val auth: FirebaseAuth) {

    val signUp: ObservableTransformer<SignUpAction, SignUpResult> = ObservableTransformer {
        it.flatMap { (email, password, username) ->
            RxAuth.createUser(auth, email, password)
                    .flatMap {
                        RxUser.updateUsername(it.user, username)
                                .andThen(Observable.just(it))
                    }
                    .map { SignUpResult.success(toUser(it.user)) }
                    .onErrorReturn { SignUpResult.failure(it.message) }
                    .startWith(SignUpResult.idle)
        }
    }

    val authTrans: ObservableTransformer<Any, SignUpResult> = ObservableTransformer {
        it.flatMap {
            RxAuth.createUser(auth, "11wangyaoda@gmail.com", "930621a123")
                    .map { SignUpResult.success(toUser(it.user)) }
                    .onErrorReturn { SignUpResult.failure(it.message) }
                    .startWith(SignUpResult.idle)
        }
    }

//    override fun signUp(action: SignUpAction): Observable<SignUpResult>
//            = RxAuth.createUser(auth, action.email, action.password)
//            .publish {
//                it.flatMapCompletable { RxUser.updateUsername(it.user, action.username) }.andThen(it)
//            }
//            .map { SignUpResult.success(toUser(it.user)) }


}

public fun toUser(user: FirebaseUser)
        = User(user.uid, user.displayName, user.email)