package br.com.alura.schedule.database.migrations;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class Migrations {
    public static final Migration[] all = {
            new Migration(1, 2) {
                @Override
                public void migrate(@NonNull SupportSQLiteDatabase database) {
                    database.execSQL("ALTER TABLE student ADD COLUMN surname TEXT");
                }
            },
            new Migration(2, 3) {
                @Override
                public void migrate(@NonNull SupportSQLiteDatabase database) {
                    // Create Table with information wished
                    // Migrate data from old table to new
                    // Remove old table
                    // Rename new table with same name the old table
                }
            }
    };
}
