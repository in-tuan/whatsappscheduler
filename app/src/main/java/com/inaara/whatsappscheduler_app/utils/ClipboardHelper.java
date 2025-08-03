package com.inaara.whatsappscheduler_app.utils;

import static android.content.Context.CLIPBOARD_SERVICE;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

public class ClipboardHelper {

    ClipboardManager clipboardManager;

    public ClipboardHelper (Context context) {
        clipboardManager = (ClipboardManager)context.getSystemService(CLIPBOARD_SERVICE);
    }

    public void copyToClipboard (String purpose, String textToCopy) {
        ClipData clip;
        clip = ClipData.newPlainText(purpose, textToCopy);
        clipboardManager.setPrimaryClip(clip);
    }
}
