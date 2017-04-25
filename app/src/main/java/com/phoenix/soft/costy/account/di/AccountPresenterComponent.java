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

package com.phoenix.soft.costy.account.di;

import com.phoenix.soft.costy.MainActivity;
import com.phoenix.soft.costy.dagger.AppComponent;
import dagger.Component;

/**
 * Created by yaoda on 24/03/17.
 */
@AccountRepoScope
@Component(modules = { AccountPresenterModule.class }, dependencies = { AppComponent.class })
public interface AccountPresenterComponent {
    void inject(MainActivity mainActivity);
}
