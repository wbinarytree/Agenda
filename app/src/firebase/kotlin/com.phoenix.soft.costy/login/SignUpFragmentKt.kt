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
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.EditText
import butterknife.BindView
import butterknife.ButterKnife
import com.phoenix.soft.costy.R

/**
 * Created by phoenix on 2017/4/16.
 */

class SignUpFragmentKt : Fragment() {

    @BindView(R.id.email)
    lateinit var etUsername: AutoCompleteTextView
    @BindView(R.id.password)
    lateinit var etPassword: EditText
    @BindView(R.id.username)
    lateinit var etNickName: EditText
    lateinit var fab: FloatingActionButton


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root: View = inflater.inflate(R.layout.fragment_sign_up, container, false)
        ButterKnife.bind(this, root)
        fab = activity.findViewById(R.id.fab) as FloatingActionButton
        return root
    }


}

fun newInstance(): SignUpFragmentKt {
    val args = Bundle()
    val fragment = SignUpFragmentKt()
    fragment.arguments = args
    return fragment
}
