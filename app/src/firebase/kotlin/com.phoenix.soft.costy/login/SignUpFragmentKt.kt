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
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.google.firebase.auth.FirebaseAuth
import com.jakewharton.rxbinding2.view.RxView
import com.phoenix.soft.costy.MainApplication
import com.phoenix.soft.costy.R
import com.phoenix.soft.costy.login.di.DaggerAuthComponent
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

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
    @Inject
    lateinit var translator: AuthTranslator
    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val root: View = inflater.inflate(R.layout.fragment_sign_up, container, false)
        ButterKnife.bind(this, root)
        fab = activity.findViewById(R.id.fab) as FloatingActionButton
        DaggerAuthComponent.builder().appComponent(
            MainApplication.getAppComponent()).build().inject(
            this)
        return root
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        RxView.clicks(fab)
            .debounce(200, TimeUnit.MILLISECONDS)
            .map {
                AuthEvent.SignUpEvent(etUsername.text.toString(), etPassword.text.toString(),
                    etNickName.text.toString())
            }
            .compose(translator.signUpProcess)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                when (it) {
                    is SignUpUiModule.Idle -> {
                        fab.setImageResource(R.drawable.ic_sync_white_24dp)
                        val animation = AnimationUtils.loadAnimation(context, R.anim.rotate)
                        fab.startAnimation(animation)
                    }
                    is SignUpUiModule.SuccessModule -> {
                        fab.setImageResource(R.drawable.ic_arrow_forward_white_24dp)
                        fab.clearAnimation()
                        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()

                    }
                    is SignUpUiModule.ErrorModule -> {
                        fab.setImageResource(R.drawable.anim_clear)
                        fab.clearAnimation()
                        Log.d("", it.msg)
                    }
                }
            })

//        RxView.clicks(fab)
//                .debounce(200, TimeUnit.MILLISECONDS)
//                .compose(translator.signUp)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({
//                    when (it) {
//                        is SignUpUiModule.Idle -> {
//                            fab.setImageResource(R.drawable.ic_sync_white_24dp)
//                            val animation = AnimationUtils.loadAnimation(context, R.anim.rotate)
//                            fab.startAnimation(animation)
//                        }
//                        is SignUpUiModule.SuccessModule -> {
//                            fab.setImageResource(R.drawable.ic_arrow_forward_white_24dp)
//                            fab.clearAnimation()
//                            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
//
//                        }
//                        is SignUpUiModule.ErrorModule -> {
//                            fab.setImageResource(R.drawable.anim_clear)
//                            fab.clearAnimation()
//                            Log.d("", it.msg)
//                        }
//                    }
//                })
    }
}

fun SignUpFragmentKt.newInstance(): SignUpFragmentKt {
    val args = Bundle()
    val fragment = SignUpFragmentKt()
    fragment.arguments = args
    return fragment
}
