package com.example.mwaldbauerscheduler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent intent2 = new Intent(context, MyIntentService.class);
        context.startService(intent2);

        //throw new UnsupportedOperationException("Not yet implemented");
    }
}
