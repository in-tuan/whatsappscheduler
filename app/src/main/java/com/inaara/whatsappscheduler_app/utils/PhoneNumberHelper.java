package com.inaara.whatsappscheduler_app.utils;


import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.util.Locale;

public class PhoneNumberHelper {

    public static boolean isPhoneNumberValid(Context context, String phoneNumber) {
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        Phonenumber.PhoneNumber number = null;
        String defaultRegion = context.getResources().getConfiguration().getLocales().get(0).getCountry();
        try {
            number = phoneUtil.parse(phoneNumber, defaultRegion);
        } catch (NumberParseException e) {
            Log.e("Main Activity", "NumberParseException was thrown: " + e.toString());
        }

        return phoneUtil.isValidNumber(number);
    }

}
