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
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.phoenix.soft.costy.R
import javax.inject.Inject

/**
 * Created by phoenix on 2017/4/16.
 */

class AuthActivityKt : AppCompatActivity() {
    private val TAG = "AuthActivity"
    @BindView(R.id.tv_sign_up)
    lateinit var tvSignUp: TextView
    @BindView(R.id.fab)
    lateinit var fab: FloatingActionButton
    @BindView(R.id.login_background)
    lateinit var root: View
    @Inject
    lateinit var auth: FirebaseAuthManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        ButterKnife.bind(this)
        tvSignUp.setText(R.string.title_about)

    }

    private fun setupSignUpText() {
        val string = getString(R.string.sign_up_long)
        val start = string.indexOf("{")
        val end = string.indexOf("}") - 1
        val ss = SpannableString(string.replace("{", "").replace("}", ""))
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(textView: View) {
                TODO()
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = true
            }
        }
        ss.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tvSignUp.text = ss
        tvSignUp.movementMethod = LinkMovementMethod.getInstance()
    }

}