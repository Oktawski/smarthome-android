package com.example.smarthome.utilities;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Arrays;

public class StringUtil {

    //todo implement checking IPv4
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static boolean isValidIpv4(String ip){
        String[] groups = ip.split("\\.");

        if(groups.length != 4) return false;

        try{
            return true;
        }
        catch (NumberFormatException e){
            return false;
        }
    }
}
