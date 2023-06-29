package br.com.alura.schedule.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Calendar;

@Entity
public class Student implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int identifier = 0;
    private String name;
    private String email;
    private Calendar createDate = Calendar.getInstance();

    @Ignore
    private Student(Parcel parcel) {
        this.identifier = parcel.readInt();
        this.name = parcel.readString();
        this.email = parcel.readString();
        this.createDate = (Calendar) parcel.readValue(Calendar.class.getClassLoader());
    }

    public static final Parcelable.Creator<Student>
            CREATOR = new Parcelable.Creator<Student>() {

        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    public Student() {
    }


    public Calendar getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Calendar createDate) {
        this.createDate = createDate;
    }

    public String getName() {
        return name;
    }


    public String getEmail() {
        return email;
    }

    public int getIdentifier() {
        return identifier;
    }

    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(identifier);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeValue(createDate);
    }

    public boolean hasValidIdentifier() {
        return identifier > 0;
    }
}
