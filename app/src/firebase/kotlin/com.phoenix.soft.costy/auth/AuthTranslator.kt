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

import android.text.TextUtils
import com.phoenix.soft.costy.auth.di.AuthScope
import com.phoenix.soft.costy.auth.events.AuthEvent
import com.phoenix.soft.costy.auth.events.AuthEvent.InputEvent
import com.phoenix.soft.costy.auth.events.AuthEvent.SignInEvent
import com.phoenix.soft.costy.auth.events.AuthEvent.SignUpEvent
import com.phoenix.soft.costy.auth.events.AuthUiModel
import com.phoenix.soft.costy.auth.events.AuthUiModel.Companion.ErrorType.EMAIL
import com.phoenix.soft.costy.auth.events.AuthUiModel.Companion.ErrorType.PASSWORD
import com.phoenix.soft.costy.auth.events.AuthUiModel.Companion.ErrorType.SIGN_IN_ERROR
import com.phoenix.soft.costy.auth.events.AuthUiModel.Companion.ErrorType.SIGN_UP_ERROR
import com.phoenix.soft.costy.auth.events.AuthUiModel.Companion.ErrorType.USERNAME
import com.phoenix.soft.costy.auth.events.SignInAction
import com.phoenix.soft.costy.auth.events.SignInResult
import com.phoenix.soft.costy.auth.events.SignUpAction
import com.phoenix.soft.costy.auth.events.SignUpResult
import com.phoenix.soft.costy.utils.Utils.isEmailValid
import com.phoenix.soft.costy.utils.Utils.isPasswordValid
import com.phoenix.soft.costy.utils.Utils.isUsername
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

/**
 * Created by yaoda on 20/04/17.
 */
@AuthScope
class AuthTranslator @Inject constructor(val auth: AuthManager) {
    val middle: PublishSubject<in AuthEvent> = PublishSubject.create<AuthEvent>()
    val res: Observable<AuthUiModel> = middle.publish {
        Observable.merge(it.ofType(SignUpEvent::class.java).signUpAction(),
            it.ofType(SignInEvent::class.java).signInAction(),
            it.ofType(InputEvent::class.java).inputAction())
    }
        .scan<AuthUiModel>(AuthUiModel.idle()) { current, new ->
            //todo more state check
            if (current is AuthUiModel.Process && new is AuthUiModel.Idle) {
                return@scan current
            }
            return@scan new
        }
        .replay(1)
        .autoConnect()

    private fun Observable<SignUpEvent>.signUpAction(): Observable<AuthUiModel> =
        this.flatMap { (email, password, username) ->
            check(email, password, username) {
                return@check Observable.just<SignUpAction>(SignUpAction(email, password, username))
                    .signUp()
                    .map {
                        when (it) {
                            is SignUpResult.SuccessResult -> AuthUiModel.success(it.user)
                            is SignUpResult.FailResult -> AuthUiModel.error(SIGN_UP_ERROR,
                                it.message)
                        }
                    }
                    .startWith(AuthUiModel.process())
                    .onErrorReturn {
                        AuthUiModel.error(SIGN_UP_ERROR, it.message ?: "Unknown Error")
                    }
            }
        }


    private fun Observable<SignInEvent>.signInAction(): Observable<AuthUiModel> =
        this.flatMap { (email, password) ->
            check(email, password) {
                return@check Observable.just(SignInAction(email, password))
                    .signIn()
                    .map {
                        when (it) {
                            is SignInResult.SuccessResult -> AuthUiModel.success(it.user)
                            is SignInResult.FailResult -> AuthUiModel.error(SIGN_IN_ERROR,
                                it.message)
                        }
                    }
                    .startWith(AuthUiModel.process())
                    .onErrorReturn {
                        AuthUiModel.error(SIGN_IN_ERROR, it.message ?: "Unknown Error")
                    }
            }

        }

    private fun Observable<InputEvent>.inputAction(): Observable<AuthUiModel> = this.flatMap {
        Observable.just(AuthUiModel.idle())
    }

    private inline fun check(email: String, password: String, username: String = "valid_username",
        action: () -> Observable<AuthUiModel>): Observable<AuthUiModel> {
        if (TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            return Observable.just(AuthUiModel.error(PASSWORD))
        }
        if (TextUtils.isEmpty(email) || !isEmailValid(email)) {
            return Observable.just(AuthUiModel.error(EMAIL))
        }
        if (!isUsername(username)) {
            return Observable.just(AuthUiModel.error(USERNAME))
        }
        return action.invoke()
    }

    fun authProcess(event: Observable<out AuthEvent>): Observable<AuthUiModel> {
        event.subscribe(middle)
        return res
    }


    private fun Observable<SignUpAction>.signUp()
        = auth.signUp(this)

    private fun Observable<SignInAction>.signIn()
        = auth.signIn(this)


}


fun Observable<out AuthEvent>.auth(
    auth: AuthTranslator): Observable<AuthUiModel> = auth.authProcess(this)






