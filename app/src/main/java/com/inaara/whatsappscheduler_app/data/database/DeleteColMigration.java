package com.inaara.whatsappscheduler_app.data.database;

import androidx.room.DeleteColumn;
import androidx.room.migration.AutoMigrationSpec;

@DeleteColumn.Entries(
        @DeleteColumn(
                tableName = "messages",
                columnName = "reminderOffset"
        )
)
public class DeleteColMigration implements AutoMigrationSpec {
}
