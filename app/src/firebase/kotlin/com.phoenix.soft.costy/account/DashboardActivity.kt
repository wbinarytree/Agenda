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

package com.phoenix.soft.costy.account

import android.animation.ValueAnimator
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.animation.AnimationUtils
import com.jakewharton.rxbinding2.view.RxView
import com.phoenix.soft.costy.R
import com.phoenix.soft.costy.R.layout
import com.phoenix.soft.costy.R.string
import com.phoenix.soft.costy.account.events.DashEvent.FabClickEvent
import com.phoenix.soft.costy.account.events.DashUiModel
import com.phoenix.soft.costy.account.events.DashUiModel.Companion.ErrorType.NETWORK
import com.phoenix.soft.costy.account.events.DashUiModel.Companion.ErrorType.NO_ACCOUNT
import com.phoenix.soft.costy.models.Account
import com.phoenix.soft.costy.transaction.TransactionAddDialogFragment
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_hidden.view_pager
import kotlinx.android.synthetic.main.activity_main.container_error
import kotlinx.android.synthetic.main.activity_main.container_no_account
import kotlinx.android.synthetic.main.activity_main.container_pager
import kotlinx.android.synthetic.main.activity_main.drawer_layout
import kotlinx.android.synthetic.main.activity_main.fab
import kotlinx.android.synthetic.main.app_bar.appbar
import kotlinx.android.synthetic.main.app_bar.tab_bar
import kotlinx.android.synthetic.main.app_bar.toolbar
import java.util.concurrent.TimeUnit

/**
 * Created by yaoda on 05/06/17.
 */
class DashboardActivity : AppCompatActivity() {
    private var isExpand: Boolean = false
    private var adapter: AccountPagerAdapter? = null
    private val disposable = CompositeDisposable()

    private val clickFab = RxView.clicks(fab).throttleFirst(200,
        TimeUnit.MILLISECONDS).map { FabClickEvent() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)
        initView()
        subscribe(DashUiModel()) {
            if (it.error) {
                container_pager.visibility = GONE
                container_error.visibility = GONE
                container_no_account.visibility = GONE
                when (it.type) {
                    NETWORK -> container_error.visibility = VISIBLE
                    NO_ACCOUNT -> container_no_account.visibility = VISIBLE
                }
            } else {
                container_pager.visibility = VISIBLE
                container_error.visibility = GONE
                container_no_account.visibility = GONE
                it.accounts?.let { DashboardActivity::updateView }
            }
            if (it.process) {
                fab.setImageResource(R.drawable.ic_sync_white_24dp)
                val animation = AnimationUtils.loadAnimation(this, R.anim.rotate)
                fab.startAnimation(animation)
            } else {
                fab.setImageResource(R.drawable.ic_add_white_24dp)
                fab.clearAnimation()
//                Snackbar.make(coordinator, Utils.fromHtml(
//                    "<font color=\"#ffffff\">" + getString(R.string.title_sync_done) + "</font>"),
//                    Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun initView() {
        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar,
            string.navigation_drawer_open,
            string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        tab_bar.setupWithViewPager(view_pager)
        view_pager.pageMargin = 20
        setSupportActionBar(toolbar)
        appbar.addOnOffsetChangedListener({ _, verticalOffset -> isExpand = verticalOffset == 0 })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return true
    }


    private fun updateView(accounts: List<Account>) {
        adapter?.apply {
            adapter = AccountPagerAdapter(supportFragmentManager, accounts)
            view_pager.adapter = adapter
        }

        //todo replace with action/events
        fab.setOnClickListener {
            val item = accounts[view_pager.currentItem]
            val frag = TransactionAddDialogFragment.newInstance(item)
            frag.show(supportFragmentManager, "add")
        }
        TODO(
            "not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun subscribe(dash: DashUiModel,
                          func: (DashUiModel) -> Unit) {
        TODO(
            "not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
}