package com.inaara.whatsappscheduler_app.ui;

import static com.inaara.whatsappscheduler_app.utils.PhoneNumberHelper.isPhoneNumberValid;
import static com.inaara.whatsappscheduler_app.whatsapp.WhatsappHelper.checkWhatsappInstalled;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.inaara.whatsappscheduler_app.R;
import com.inaara.whatsappscheduler_app.data.database.AppDatabase;
import com.inaara.whatsappscheduler_app.data.model.Message;
import com.inaara.whatsappscheduler_app.scheduler.Scheduler;
import com.inaara.whatsappscheduler_app.whatsapp.WhatsappHelper;

import java.util.Calendar;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    // MANAGING FRONTEND
    private long scheduledTimeMs = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText contactInput = findViewById(R.id.contact_input);
        EditText messageInput = findViewById(R.id.message_input);
        EditText phoneNumberInput = findViewById(R.id.phone_num_input);
        Button timePickerButton = findViewById(R.id.time_picker);
        TextView timePickerErrorViewer = findViewById(R.id.time_picker_error_view);
        Button scheduleButton = findViewById(R.id.schedule_button);

        // Time Picker logic
        timePickerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int yearCurrent = calendar.get(Calendar.YEAR);
                int monthCurrent = calendar.get(Calendar.MONTH);
                int dayCurrent = calendar.get(Calendar.DAY_OF_MONTH);
                int hourCurrent = calendar.get(Calendar.HOUR_OF_DAY);
                int minCurrent = calendar.get(Calendar.MINUTE);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        MainActivity.this,
                        R.style.CustomDatePickerDialogTheme,
                        //date picker listener = param 3 to instantiate DatePickerDialog
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                calendar.set(Calendar.YEAR, year);
                                calendar.set(Calendar.MONTH, month);
                                calendar.set(Calendar.DAY_OF_MONTH, day);

                                TimePickerDialog timePickerDialog = new TimePickerDialog(
                                        MainActivity.this,
                                        R.style.CustomTimePickerDialogTheme,
                                        // time picker listener = param 3 to instantiate TimePickerDialog
                                        new TimePickerDialog.OnTimeSetListener() {
                                            @Override
                                            public void onTimeSet(TimePicker timePicker, int hour, int min) {
                                                calendar.set(Calendar.HOUR_OF_DAY, hour);
                                                calendar.set(Calendar.MINUTE, min);
                                                scheduledTimeMs = calendar.getTimeInMillis();
                                            }
                                        },
                                        // arguments for Time Picker
                                        hourCurrent,
                                        minCurrent,
                                        false
                                );
                                timePickerDialog.show();
                            }
                        },
                        // arguments for Date Picker
                        yearCurrent,
                        monthCurrent,
                        dayCurrent
                );
                datePickerDialog.show();
            }
        });

        // Schedule Button logic
        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check if Whatsapp is installed
                checkWhatsappInstalled(MainActivity.this);

                if (areInputsValid(contactInput, messageInput,
                        phoneNumberInput, timePickerErrorViewer, scheduledTimeMs)) {

                    // Backend handling + call to scheduler
                    try {
                        String contactName = contactInput.getText().toString();
                        String phoneNumber = phoneNumberInput.getText().toString();
                        String messageText = messageInput.getText().toString();

                        Message message = new Message();
                        message.setContactName(contactName);
                        message.setPhoneNumber(phoneNumber);
                        message.setText(messageText);
                        message.setScheduledTimeMs(scheduledTimeMs);

                        AppDatabase db = AppDatabase.getInstance(MainActivity.this);

                        Executors.newSingleThreadExecutor().execute(() -> {
                            long id = db.messageDao().insert(message);
                            message.setId((int) id);
                            Scheduler scheduler = new Scheduler();
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                                scheduler.scheduleMessage(MainActivity.this, message);
                            }

                            runOnUiThread(() -> {
                                Toast.makeText(MainActivity.this, "Message scheduled",
                                        Toast.LENGTH_SHORT).show();
                                contactInput.setText("");
                                messageInput.setText("");
                                phoneNumberInput.setText("");
                                timePickerErrorViewer.setText("");
                                timePickerErrorViewer.setVisibility(View.INVISIBLE);
                            });

                        });
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "Error scheduling message",
                                Toast.LENGTH_SHORT).show();
                        throw new RuntimeException(e);
                    }


                }
            }
        });

        // Check flag to request permission
        if (getIntent().getBooleanExtra("request_permission", false)) {
            checkNotificationPermission(this);
        }
    }

    public boolean areInputsValid(EditText contact,
                                  EditText message,
                                  EditText phoneNumber,
                                  TextView timePickerViewer,
                                  long scheduledTime) {
        if (!isRequiredFieldValid(contact)) return false;
        if (!isRequiredFieldValid(message)) return false;
        if(phoneNumber.length() != 0) {
            if(!isPhoneNumberValid(MainActivity.this, phoneNumber.getText().toString())) {
                phoneNumber.setError("Phone number is invalid.");
                return false;
            }
        }

        if ((scheduledTime <= System.currentTimeMillis())) {
            timePickerViewer.setVisibility(View.VISIBLE);
            timePickerViewer.setError("");
            timePickerViewer.setText("Time entered must be in future.");
            return false;
        }
        return true;
    }

    private boolean isRequiredFieldValid(EditText field) {
        if (field.length() == 0) {
            field.setError("Required field.");
            return false;
        }
        return true;
    }


    // MANAGING PERMISSIONS
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
