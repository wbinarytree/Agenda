package com.phoenix.soft.agenda.utils;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Patterns;
import android.view.View;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yaoda on 02/03/17.
 */

public class Utils {
    private static final String USERNAME_PATTERN = "^[A-Za-z0-9]+(?:[ _-][A-Za-z0-9]+)*$";

    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html){
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html,Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }

    public static void viewMoveToCenter(View view, Activity context, Animator.AnimatorListener listener){
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
