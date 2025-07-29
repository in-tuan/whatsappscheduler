package com.inaara.whatsappscheduler_app.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.inaara.whatsappscheduler_app.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check flag to request permission
        if (getIntent().getBooleanExtra("request_permission", false)) {
            checkNotificationPermission(this);
        }
    }

    final int REQUEST_NOTIFICATION_PERMISSION = 1001;
    public void checkNotificationPermission(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (context.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) !=
                    (PackageManager.PERMISSION_GRANTED)) {
                // Toast.makeText(context, "Enable notifications permission", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        REQUEST_NOTIFICATION_PERMISSION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            // further permission requests can be added here if added
            case REQUEST_NOTIFICATION_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Notification permission granted", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(this,
                            "Notification permission denied. App will not be able to " +
                                    "function as intended due to user's notification preferences.",
                            Toast.LENGTH_LONG).show();
                }
                return;
        }
    }

}
