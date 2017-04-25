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

import com.google.firebase.auth.FirebaseAuth
import com.phoenix.soft.costy.login.AuthManager
import com.phoenix.soft.costy.login.FirebaseAuthManager
import dagger.Module
import dagger.Provides

/**
 * Created by yaoda on 25/04/17.
 */
@Module
class AuthModule {
    @AuthScope
    @Provides
    fun provideManager(auth: FirebaseAuth): AuthManager = FirebaseAuthManager(auth)
}