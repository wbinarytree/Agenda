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

package com.phoenix.soft.costy.auth

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import butterknife.ButterKnife
import com.google.firebase.auth.FirebaseAuth
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.phoenix.soft.costy.MainApplication
import com.phoenix.soft.costy.R
import com.phoenix.soft.costy.auth.events.AuthEvent.InputEvent
import com.phoenix.soft.costy.auth.events.AuthEvent.SignUpEvent
import com.phoenix.soft.costy.auth.events.AuthUiModel
import com.phoenix.soft.costy.auth.events.AuthUiModel.Companion.ErrorType.EMAIL
import com.phoenix.soft.costy.auth.events.AuthUiModel.Companion.ErrorType.PASSWORD
import com.phoenix.soft.costy.auth.events.AuthUiModel.Companion.ErrorType.USERNAME
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_sign_up.email
import kotlinx.android.synthetic.main.fragment_sign_up.password
import kotlinx.android.synthetic.main.fragment_sign_up.username
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by phoenix on 2017/4/16.
 */

class SignUpFragmentKt : Fragment() {

    lateinit var fab: FloatingActionButton
    @Inject lateinit var translator: AuthTranslator
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val disposables: CompositeDisposable = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val root: View = inflater.inflate(R.layout.fragment_sign_up, container, false)
        ButterKnife.bind(this, root)
        MainApplication.getAuthComponent().inject(this)
        fab = activity.findViewById(R.id.fab) as FloatingActionButton
        return root
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val clickObservable = RxView.clicks(fab)
            .throttleFirst(200, TimeUnit.MILLISECONDS)
            .map {
                SignUpEvent(email.text.toString(), password.text.toString(),
                    username.text.toString())
            }

        val inputObservable = Observable.merge(
            RxTextView.afterTextChangeEvents(email).debounce(300, TimeUnit.MILLISECONDS),
            RxTextView.afterTextChangeEvents(password).debounce(300, TimeUnit.MILLISECONDS),
            RxTextView.afterTextChangeEvents(username).debounce(300, TimeUnit.MILLISECONDS)
        ).map { InputEvent(it.editable().toString()) }

        val disposable = Observable.merge(clickObservable, inputObservable)
            .auth(translator)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                when (it) {
                    is AuthUiModel.Idle -> {
                        fab.setImageResource(R.drawable.ic_arrow_forward_white_24dp)
                        fab.clearAnimation()
                    }
                    is AuthUiModel.SuccessModel -> {
                        fab.setImageResource(R.drawable.ic_check_white_24dp)
                        fab.clearAnimation()
                        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                    }
                    is AuthUiModel.ErrorModel -> {
                        fab.setImageResource(R.drawable.anim_clear)
                        fab.clearAnimation()
                        when (it.type) {
                            EMAIL -> email.error = it.msg
                            PASSWORD -> password.error = it.msg
                            USERNAME -> username.error = it.msg
                            else -> Toast.makeText(context, it.msg, Toast.LENGTH_SHORT).show()
                        }
                    }
                    is AuthUiModel.Process -> {
                        fab.setImageResource(R.drawable.ic_sync_white_24dp)
                        val animation = AnimationUtils.loadAnimation(context, R.anim.rotate)
                        fab.startAnimation(animation)
                    }

                }
            }, {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }, {
                throw RuntimeException("This Observable Shouldn't stop")
            }, {
                Log.d("disposable", "starting")
            })


        disposables.add(disposable)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    companion object {
        fun newInstance(): SignUpFragmentKt {
            val args = Bundle()
            val fragment = SignUpFragmentKt()
            fragment.arguments = args
            return fragment
        }

        val TAG = "SIGN_UP_FRAGMENT_KT"
    }
}




