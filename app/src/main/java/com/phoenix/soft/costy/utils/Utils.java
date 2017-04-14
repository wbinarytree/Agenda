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

package com.phoenix.soft.costy.utils;

import android.animation.Animator;
import android.app.Activity;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.regex.Pattern;

/**
 * Created by yaoda on 02/03/17.
 */

public class Utils {
    private static final String USERNAME_PATTERN = "^[A-Za-z0-9]+(?:[ _-][A-Za-z0-9]+)*$";

    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html) {
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }

    public static void viewMoveToCenter(View view, Activity context, Animator.AnimatorListener listener) {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int originalPos[] = new int[2];
        view.getLocationOnScreen(originalPos);
        int xDest = dm.widthPixels / 2;
        xDest -= (view.getMeasuredWidth() / 2);
        view.animate().translationX(xDest - originalPos[0]).setListener(listener);
    }

    public static boolean isEmailValid(String target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() >= 6;
    }

    public static boolean isUsername(String target) {
        return Pattern.compile(USERNAME_PATTERN).matcher(target).matches();
    }
}
