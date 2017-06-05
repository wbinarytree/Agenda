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

import com.phoenix.soft.costy.auth.events.AuthUiModel.Companion.ErrorType.EMAIL
import com.phoenix.soft.costy.auth.events.AuthUiModel.Companion.ErrorType.PASSWORD
import com.phoenix.soft.costy.auth.events.AuthUiModel.Companion.ErrorType.USERNAME
import com.phoenix.soft.costy.models.User

data class AuthUiModel(val msg: String? = null,
                       val usernameError: String? = null,
                       val passwordError: String? = null,
                       val emailError: String? = null,
                       val errorType: ErrorType? = null,
                       val user: User? = null,
                       val process: Boolean = false) {

    companion object {
        fun error(type: ErrorType,
                  msg: String = type.name + " is not validated"): AuthUiModel = when (type) {
            USERNAME -> AuthUiModel(usernameError = msg, errorType = type)
            PASSWORD -> AuthUiModel(passwordError = msg, errorType = type)
            EMAIL -> AuthUiModel(emailError = msg, errorType = type)
            else -> AuthUiModel(msg = msg, errorType = type)
        }

        fun success(user: User) = AuthUiModel(user = user, process = false)

        fun idle() = AuthUiModel()
        fun process() = AuthUiModel(process = true)

        enum class ErrorType {
            USERNAME, PASSWORD, EMAIL, UNKNOWN, SIGN_UP_ERROR,
            SIGN_IN_ERROR
        }
    }

}