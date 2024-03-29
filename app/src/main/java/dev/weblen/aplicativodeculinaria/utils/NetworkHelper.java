package dev.weblen.aplicativodeculinaria.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkHelper {

    public static boolean isInternetAvailable(Activity object) {
        ConnectivityManager cm = (ConnectivityManager) object.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfo = null;
        if (cm != null) {
            netInfo = cm.getActiveNetworkInfo();
        }

        return (netInfo != null && netInfo.isConnected());
    }
}
