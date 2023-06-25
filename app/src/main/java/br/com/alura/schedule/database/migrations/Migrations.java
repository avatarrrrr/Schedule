package br.com.alura.schedule.database.migrations;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class Migrations {
    public static final Migration[] all = {
            new Migration(1, 2) {
                @Override
                public void migrate(@NonNull SupportSQLiteDatabase database) {
                    database.execSQL("ALTER TABLE Student ADD COLUMN surname TEXT");
                }
            },
            new Migration(2, 3) {
                @Override
                public void migrate(@NonNull SupportSQLiteDatabase database) {
                    // Create Table with information wished
                    database.execSQL("CREATE TABLE IF NOT EXISTS `Student_new` (`identifier` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT, `telephone` TEXT, `email` TEXT)");
                    // Migrate data from old table to new
                    database.execSQL("INSERT INTO Student_new (identifier, name, telephone, email) SELECT identifier, name, telephone, email FROM Student");
                    // Remove old table
                    database.execSQL("DROP TABLE Student");
                    // Rename new table with same name the old table
                    database.execSQL("ALTER TABLE Student_new RENAME TO Student");
                }
            },
            new Migration(3, 4) {
                @Override
                public void migrate(@NonNull SupportSQLiteDatabase database) {
                    database.execSQL("ALTER TABLE Student ADD COLUMN createDate INTEGER");
                }
            }
    };
}
