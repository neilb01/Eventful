package com.example.greenteam.eventfulevents.Utility;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by NB on 2016-11-18.
 */

public class Utility {


    public static boolean hasConnection(Context context)
    {
        ConnectivityManager cManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo network = cManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (network != null && network.isConnected())
        {
            return true;
        }

        network = cManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (network != null && network.isConnected())
        {
            return true;
        }

        NetworkInfo activeNetwork = cManager.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected())
        {
            return true;
        }
        return false;
    }

    public static void hideSoftKeyboard(Activity context) {

        if (context.getCurrentFocus() != null)
        {
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), 0);
        }
    }

}
