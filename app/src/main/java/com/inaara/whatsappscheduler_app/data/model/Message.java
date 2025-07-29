package com.inaara.whatsappscheduler_app.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/*
* Entity representing a scheduled message stored in the Room database.
*/
@Entity(tableName = "messages")
public class Message {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "contact_name")
    private String contactName;

    @ColumnInfo(name = "phone_number")
    private String phoneNumber;

    @ColumnInfo(name = "message_content")
    private String text;

    @ColumnInfo(name="scheduled_time_ms")
    private long scheduledTimeMs;   // Time in epoch milliseconds

    @ColumnInfo(name="reminderOffset")
    private int reminderOffset;    // Minutes before to trigger reminder


    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getContactName() { return contactName; }
    public void setContactName(String contactName) { this.contactName = contactName; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public long getScheduledTimeMs() { return scheduledTimeMs; }
    public void setScheduledTimeMs (long scheduledTimeMs){ this.scheduledTimeMs = scheduledTimeMs; }

    public int getReminderOffset() { return reminderOffset; }
    public void setReminderOffset (int reminderOffset){ this.reminderOffset = reminderOffset; }
}
