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

sealed class SignUpUiModule {
    companion object {
        fun error(type: ErrorType, msg: String = type.name + " is not validated"):
                SignUpUiModule = ErrorModule(type, msg)

        fun success(user: User): SignUpUiModule = SuccessModule(user)
        val inProcess: SignUpUiModule = Idle()

        enum class ErrorType {
            USERNAME,
            PASSWORD,
            EMAIL,
            UNKNOWN,
            SIGN_UP_ERROR
        }
    }

    data class ErrorModule(val type: ErrorType, val msg: String) : SignUpUiModule()
    data class SuccessModule(val user: User) : SignUpUiModule()
    class Idle : SignUpUiModule()
}

