package com.example.whatsappclone;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class NetworkAvailability {
    static Context context;
    public NetworkAvailability(Context context){
        this.context=context;
    }
    public boolean isNetWorkAvailable() {
        try {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = null;
            if (manager != null) {
                networkInfo = manager.getActiveNetworkInfo();
            }
            return networkInfo != null && networkInfo.isConnectedOrConnecting();
        } catch (NullPointerException e) {
            return false;
        }
    }
}
