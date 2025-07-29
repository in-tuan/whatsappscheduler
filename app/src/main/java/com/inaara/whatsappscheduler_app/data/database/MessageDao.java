package com.inaara.whatsappscheduler_app.data.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.inaara.whatsappscheduler_app.data.model.Message;

@Dao
public interface MessageDao {

    @Insert
    public long insert(Message message);

    @Delete
    public void delete(Message message);

    @Query("SELECT * FROM messages WHERE id = :id")
    Message getById(int id);

    @Query("SELECT * FROM messages")
    public Message[] loadAllMessages();

}
