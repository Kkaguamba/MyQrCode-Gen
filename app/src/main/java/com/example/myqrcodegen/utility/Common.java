package com.example.myqrcodegen.utility;

import android.content.Context;
import android.net.ConnectivityManager;

public class Common {
    public static boolean isConnectedToInternet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            android.net.NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == android.net.NetworkInfo.State.CONNECTED)
                        return true;
                }
            }
        }
        return false;
    }
}


