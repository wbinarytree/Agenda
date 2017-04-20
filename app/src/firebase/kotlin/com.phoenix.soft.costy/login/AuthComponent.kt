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

package com.phoenix.soft.costy.login.di

import com.phoenix.soft.costy.dagger.AppComponent
import com.phoenix.soft.costy.login.AuthActivityKt
import com.phoenix.soft.costy.login.FirebaseAuthManager
import dagger.Component
import javax.inject.Scope

/**
 * Created by phoenix on 2017/4/16.
 */
@AuthScope
@Component(dependencies = arrayOf(AppComponent::class))
interface AuthComponent {
    fun inject(authActivity: AuthActivityKt)
    fun provide(): FirebaseAuthManager
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class AuthScope
