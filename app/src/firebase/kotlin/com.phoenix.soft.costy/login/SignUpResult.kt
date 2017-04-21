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

import com.phoenix.soft.costy.models.User

sealed class SignUpResult {
    class SuccessResult(val user: User) : SignUpResult()

    class IdleResult : SignUpResult()

    class FailResult(val message: String) : SignUpResult()

    companion object {
        val idle: SignUpResult = IdleResult()

        fun success(user: User): SignUpResult = SuccessResult(user)

        fun failure(message: String?): SignUpResult = FailResult(message ?: "Sign Up Error With unknown message")

    }

}






