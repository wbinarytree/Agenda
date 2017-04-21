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

import android.text.TextUtils
import com.phoenix.soft.costy.login.SignUpUiModule.Companion.ErrorType.*
import com.phoenix.soft.costy.login.di.AuthScope
import com.phoenix.soft.costy.utils.Utils.*
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import javax.inject.Inject

/**
 * Created by yaoda on 20/04/17.
 */
@AuthScope
class AuthTranslator @Inject constructor(val auth: FirebaseAuthManager) {

    val signUpResult: ObservableTransformer<SignUpResult, SignUpUiModule> = ObservableTransformer {
        it.map {
            when (it) {
                is SignUpResult.IdleResult -> SignUpUiModule.inProcess
                is SignUpResult.SuccessResult -> SignUpUiModule.success(it.user)
                is SignUpResult.FailResult -> SignUpUiModule.error(SIGN_UP_ERROR, it.message)
                else -> SignUpUiModule.error(UNKNOWN)
            }
        }
    }

    val signUp: ObservableTransformer<Any, SignUpUiModule> = ObservableTransformer {
        it.map { SignUpAction("11wangyaoda@gmail.com", "930621a123", "username") }
                .compose(auth.signUp)
                .compose(signUpResult)
    }

    val signUpProcess: ObservableTransformer<AuthEvent, SignUpUiModule> = ObservableTransformer {
        it.flatMap {
            event ->
            if (event is AuthEvent.SignUpEvent) {
                val result = checkEvent(event)
                when (result) {
                    is SignUpUiModule -> Observable.just(result)
                    is SignUpAction -> Observable.just(result).compose(auth.signUp).compose(signUpResult)
                    else -> {
                        Observable.just(SignUpUiModule.error(UNKNOWN))
                    }
                }
            } else {
                TODO()
            }

        }
    }

    fun checkEvent(event: AuthEvent.SignUpEvent): Any {
        val (email, password, username) = event
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            return SignUpUiModule.error(PASSWORD)
        }
        if (TextUtils.isEmpty(email)) {
            return SignUpUiModule.error(EMAIL)

        } else if (!isEmailValid(email)) {
            return SignUpUiModule.error(EMAIL)

        } else if (!isUsername(username)) {
            return SignUpUiModule.error(USERNAME)
        }
        return SignUpAction(email, password, username)

    }

}

data class SignUpAction(val email: String, val password: String, val username: String)
