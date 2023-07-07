package br.com.alura.schedule.models;

import static androidx.room.ForeignKey.CASCADE;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import br.com.alura.schedule.models.enums.TelephoneType;

@Entity
public class Telephone implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int identifier;
    @ForeignKey(
            entity = Student.class,
            parentColumns = "identifier",
            childColumns = "studentIdentifier",
            onUpdate = CASCADE,
            onDelete = CASCADE
    )
    private int studentIdentifier;
    private String number;
    private TelephoneType telephoneType;

    @Ignore
    protected Telephone(Parcel in) {
        identifier = in.readInt();
        studentIdentifier = in.readInt();
        number = in.readString();
        telephoneType = (TelephoneType) in.readValue(TelephoneType.class.getClassLoader());
    }

    public Telephone() {
    }

    public static final Creator<Telephone> CREATOR = new Creator<Telephone>() {
        @Override
        public Telephone createFromParcel(Parcel in) {
            return new Telephone(in);
        }

        @Override
        public Telephone[] newArray(int size) {
            return new Telephone[size];
        }
    };

    public int getIdentifier() {
        return identifier;
    }

    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public TelephoneType getTelephoneType() {
        return telephoneType;
    }

    public void setTelephoneType(TelephoneType telephoneType) {
        this.telephoneType = telephoneType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(identifier);
        dest.writeInt(studentIdentifier);
        dest.writeString(number);
        dest.writeValue(telephoneType);
    }

    public int getStudentIdentifier() {
        return studentIdentifier;
    }

    public void setStudentIdentifier(int studentIdentifier) {
        this.studentIdentifier = studentIdentifier;
    }
}
