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
import com.phoenix.soft.costy.models.User
import io.reactivex.Observable
import phoenixlib.io.rxfirebase.auth.RxAuth
import phoenixlib.io.rxfirebase.auth.RxUser

/**
 * Created by phoenix on 2017/4/16.
 */
class AuthManager constructor(val auth: FirebaseAuth) {
    fun signUp(email: String, pwd: String, username: String):Observable<User>
            = RxAuth.createUser(auth, email, pwd)
            .map { it.user }
            .publish { user ->
                user.flatMapCompletable { RxUser.updateUsername(it, username) }
                        .andThen(user)
            }
            .map(::toUser)

    fun signIn(email: String, pwd: String)
            = RxAuth.signInWithEmailAndPassword(auth, email, pwd)
}

private fun toUser(user: FirebaseUser)
        = User(user.uid, user.displayName, user.email)
