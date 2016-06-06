package com.phoenix.soft.agenda;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by phoenix on 16/6/5.
 */

public class ToastReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "TEST", Toast.LENGTH_SHORT).show();
    }
}
