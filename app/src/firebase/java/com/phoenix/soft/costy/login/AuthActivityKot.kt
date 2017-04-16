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

import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.phoenix.soft.costy.R
import javax.inject.Inject

/**
 * Created by phoenix on 2017/4/16.
 */

class AuthActivityKot : AppCompatActivity() {
    private val TAG = "AuthActivity"
    @BindView(R.id.tv_sign_up)
    lateinit var tvSignUp: TextView
    @BindView(R.id.fab)
    lateinit var fab: FloatingActionButton
    @BindView(R.id.login_background)
    lateinit var root: View
    lateinit var auth: AuthManager

    override fun onCreate(savedInstanceState: Bundle?,
            persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_login)
        ButterKnife.bind(this)
        tvSignUp.setText(R.string.title_about)


    }

    companion object {
        private val ALPHA_FADE = ButterKnife.Action<View> { view, index ->
            with(AlphaAnimation(0f, 1f)) {
                fillBefore = true
                duration = 500
                startOffset = (index * 100).toLong()
                view.startAnimation(this)
            }
        }
    }
}