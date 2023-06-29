package br.com.alura.schedule.database.migrations;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class Migrations {
    final static private String databaseName = "Student";
    final static private String newDatabaseName = "Student_new";

    public static final Migration[] all = {
            new Migration(1, 2) {
                @Override
                public void migrate(@NonNull SupportSQLiteDatabase database) {
                    database.execSQL("ALTER TABLE " + databaseName + " ADD COLUMN surname TEXT");
                }
            },
            new Migration(2, 3) {
                @Override
                public void migrate(@NonNull SupportSQLiteDatabase database) {
                    // Create Table with information wished
                    database.execSQL("CREATE TABLE IF NOT EXISTS " + newDatabaseName + " (`identifier` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT, `telephone` TEXT, `email` TEXT)");
                    // Migrate data from old table to new
                    database.execSQL("INSERT INTO " + newDatabaseName + " (identifier, name, telephone, email) SELECT identifier, name, telephone, email FROM " + databaseName);
                    removeAndRename(database);
                }
            },
            new Migration(3, 4) {
                @Override
                public void migrate(@NonNull SupportSQLiteDatabase database) {
                    database.execSQL("ALTER TABLE " + databaseName + " ADD COLUMN createDate INTEGER");
                }
            },
            new Migration(4, 5) {
                @Override
                public void migrate(@NonNull SupportSQLiteDatabase database) {
                    database.execSQL("CREATE TABLE IF NOT EXISTS " + newDatabaseName + " (`identifier` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT, `landline` TEXT, `email` TEXT, `createDate` INTEGER, `cellPhone` TEXT)");
                    database.execSQL("INSERT INTO " + newDatabaseName + " (identifier, name, landline, email, createDate) SELECT identifier, name, telephone, email, createDate FROM " + databaseName);
                    removeAndRename(database);
                }
            }
    };

    private static void removeAndRename(@NonNull SupportSQLiteDatabase database) {
        // Remove old table
        database.execSQL("DROP TABLE " + databaseName);
        // Rename new table with same name the old table
        database.execSQL("ALTER TABLE " + newDatabaseName + " RENAME TO " + databaseName);
    }
}
