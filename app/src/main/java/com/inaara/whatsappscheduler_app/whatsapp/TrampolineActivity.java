package com.inaara.whatsappscheduler_app.whatsapp;

import static com.inaara.whatsappscheduler_app.whatsapp.WhatsappHelper.openWhatsapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.inaara.whatsappscheduler_app.data.database.AppDatabase;
import com.inaara.whatsappscheduler_app.data.model.Message;

import java.util.concurrent.Executors;

public class TrampolineActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toast.makeText(this, "In Activity Instance", Toast.LENGTH_SHORT).show();

        AppDatabase dbInstance = AppDatabase.getInstance(this);
        int message_id = getIntent().getIntExtra("message_id", 0);

        Executors.newSingleThreadExecutor().execute(() -> {
            Message message = dbInstance.messageDao().getById(message_id);

            String phoneNumber = message.getPhoneNumber();

            runOnUiThread(() -> {
                openWhatsapp(this, phoneNumber, message.getText());
                finish();
            });
        });

}

}
