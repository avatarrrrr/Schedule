package br.com.alura.schedule.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Student implements Parcelable {
    private int identifier = 0;
    private  String name;
    private  String telephone;
    private  String email;

    public Student(String name, String telephone, String email) {
        this.name = name;
        this.telephone = telephone;
        this.email = email;
    }

    private Student(Parcel parcel) {
        this.identifier = parcel.readInt();
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

    public Student() {
        this.name = "";
        this.telephone = "";
        this.email = "";
    }

    public String getName() {
        return name;
    }

    public String getTelephone() {
        return telephone;
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

    public void setTelephone(String telephone) {
        this.telephone = telephone;
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
        dest.writeString(telephone);
        dest.writeString(email);
    }

    public boolean hasValidIdentifier() {
        return identifier > 0;
    }
}
