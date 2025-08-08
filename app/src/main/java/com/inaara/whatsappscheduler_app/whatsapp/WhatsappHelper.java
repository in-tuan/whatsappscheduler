package com.inaara.whatsappscheduler_app.whatsapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class WhatsappHelper {

    static PackageManager packageManager;

    public static void checkWhatsappInstalled(Context context) {
        packageManager = context.getPackageManager();
        try {
            packageManager.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(context, "Whatsapp NOT installed", Toast.LENGTH_SHORT).show();
        }
    }

    public static void openWhatsapp(Context context, String phoneNum, String message) {
        String url;
        try {
            url = "https://wa.me/" + phoneNum + "?text=" + URLEncoder.encode(message, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Toast.makeText(context, "Couldn't open WhatsApp.", Toast.LENGTH_SHORT).show();
            throw new RuntimeException(e);
        }

        Intent whatsappIntent = new Intent(Intent.ACTION_VIEW);
        whatsappIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        whatsappIntent.setData(Uri.parse(url));
        whatsappIntent.setPackage("com.whatsapp");

        packageManager = context.getPackageManager();

        if (whatsappIntent.resolveActivity(packageManager) != null) {
            context.startActivity(whatsappIntent);
        } else {
            Toast.makeText(context, "WhatsApp not installed", Toast.LENGTH_SHORT).show();
        }
    }

}
