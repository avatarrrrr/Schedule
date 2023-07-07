package br.com.alura.schedule.database.migrations;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import br.com.alura.schedule.models.enums.TelephoneType;

public class Migrations {
    private Migrations(){}
    private static final String STUDENT_DATABASE_NAME = "Student";
    private static final String NEW_STUDENT_DATABASE_NAME = "Student_new";
    private static final String TELEPHONE_DATABASE_NAME = "Telephone";

    public static final Migration[] all = {new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE " + STUDENT_DATABASE_NAME + " ADD COLUMN surname TEXT");
        }
    }, new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Create Table with information wished
            database.execSQL("CREATE TABLE IF NOT EXISTS " + NEW_STUDENT_DATABASE_NAME + " (`identifier` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT, `telephone` TEXT, `email` TEXT)");
            // Migrate data from old table to new
            database.execSQL("INSERT INTO " + NEW_STUDENT_DATABASE_NAME + " (identifier, name, telephone, email) SELECT identifier, name, telephone, email FROM " + STUDENT_DATABASE_NAME);
            removeAndRename(database);
        }
    }, new Migration(3, 4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE " + STUDENT_DATABASE_NAME + " ADD COLUMN createDate INTEGER");
        }
    }, new Migration(4, 5) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS " + NEW_STUDENT_DATABASE_NAME + " (`identifier` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT, `landline` TEXT, `email` TEXT, `createDate` INTEGER, `cellPhone` TEXT)");
            database.execSQL("INSERT INTO " + NEW_STUDENT_DATABASE_NAME + " (identifier, name, landline, email, createDate) SELECT identifier, name, telephone, email, createDate FROM " + STUDENT_DATABASE_NAME);
            removeAndRename(database);
        }
    }, new Migration(5, 6) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS " + NEW_STUDENT_DATABASE_NAME + " (`identifier` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT, `email` TEXT, `createDate` INTEGER)");
            database.execSQL("INSERT INTO " + NEW_STUDENT_DATABASE_NAME + " (identifier, name, email, createDate) SELECT identifier, name, email, createDate FROM " + STUDENT_DATABASE_NAME);
            database.execSQL("CREATE TABLE IF NOT EXISTS " + TELEPHONE_DATABASE_NAME + " (`identifier` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `number` TEXT, `telephoneType` TEXT)");
            database.execSQL("ALTER TABLE " + TELEPHONE_DATABASE_NAME + " ADD COLUMN studentIdentifier INTEGER NOT NULL");
            database.execSQL("INSERT INTO " + TELEPHONE_DATABASE_NAME + " (number, studentIdentifier) SELECT landline, identifier FROM " + STUDENT_DATABASE_NAME + " WHERE landline IS NOT NULL");
            database.execSQL("INSERT INTO " + TELEPHONE_DATABASE_NAME + " (number, studentIdentifier) SELECT cellPhone, identifier FROM " + STUDENT_DATABASE_NAME + " WHERE cellPhone IS NOT NULL");
            database.execSQL("UPDATE " + TELEPHONE_DATABASE_NAME + " SET telephoneType = ?", new TelephoneType[]{TelephoneType.LANDLINE});
            removeAndRename(database);
        }
    }};

    private static void removeAndRename(@NonNull SupportSQLiteDatabase database) {
        // Remove old table
        database.execSQL("DROP TABLE " + STUDENT_DATABASE_NAME);
        // Rename new table with same name the old table
        database.execSQL("ALTER TABLE " + NEW_STUDENT_DATABASE_NAME + " RENAME TO " + STUDENT_DATABASE_NAME);
    }
}
