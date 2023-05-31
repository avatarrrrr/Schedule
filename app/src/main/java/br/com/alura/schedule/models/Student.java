package br.com.alura.schedule.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Student implements Parcelable {
    private final String name;
    private final String telephone;
    private final String email;

    public Student(String name, String telephone, String email) {
        this.name = name;
        this.telephone = telephone;
        this.email = email;
    }

    private Student(Parcel parcel) {
        this.name = parcel.readString();
        this.telephone = parcel.readString();
        this.email = parcel.readString();
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

    public String getName() {
        return name;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getEmail() {
        return email;
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
        dest.writeString(name);
        dest.writeString(telephone);
        dest.writeString(email);
    }
}
