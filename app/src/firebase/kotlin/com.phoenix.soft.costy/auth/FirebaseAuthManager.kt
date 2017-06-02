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

package com.phoenix.soft.costy.auth

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.phoenix.soft.costy.auth.events.SignInAction
import com.phoenix.soft.costy.auth.events.SignInResult
import com.phoenix.soft.costy.auth.events.SignUpAction
import com.phoenix.soft.costy.auth.events.SignUpResult
import com.phoenix.soft.costy.models.User
import io.reactivex.Observable
import phoenixlib.io.rxfirebase.auth.RxAuth
import phoenixlib.io.rxfirebase.auth.RxUser

/**
 * Created by phoenix on 2017/4/16.
 */

class FirebaseAuthManager constructor(val auth: FirebaseAuth) : AuthManager {
    override fun signIn(upstream: Observable<SignInAction>): Observable<SignInResult> {
        TODO(
            "not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun signUp(upstream: Observable<SignUpAction>): Observable<SignUpResult>
        = upstream.flatMap { (email, password, username) ->
        RxAuth.createUser(auth, email, password)
            .flatMap {
                RxUser.updateUsername(it.user, username)
                    .andThen(Observable.just(it))
            }
            .map {
                Log.d("Auth", "success")
                SignUpResult.success(toUser(it.user))
            }
            .onErrorReturn {
                Log.d("Auth", "fail")
                SignUpResult.failure(it.message)
            }
    }


    companion object {
        fun toUser(user: FirebaseUser)
            = User(user.uid, user.displayName, user.email)

    }

}

