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

package com.phoenix.soft.costy.auth.events

import com.phoenix.soft.costy.common.Result
import com.phoenix.soft.costy.models.User

sealed class SignUpResult : Result() {
    class SuccessResult(val user: User) : SignUpResult()

    class FailResult(val message: String) : SignUpResult()

    companion object {
        fun success(user: User): SignUpResult = SuccessResult(user)

        fun failure(message: String?): SignUpResult = FailResult(
            message ?: "Sign Up Error With unknown message")
    }
}

sealed class SignInResult : Result() {

    class SuccessResult(val user: User) : SignInResult()


    class FailResult(val message: String) : SignInResult()

    companion object {
        fun success(user: User): SignInResult = SuccessResult(user)

        fun failure(message: String?): SignInResult = FailResult(
            message ?: "Sign In Error With unknown message")
    }
}






