package br.com.alura.schedule.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import br.com.alura.schedule.models.enums.TelephoneType;

@Entity
public class Telephone implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String number;
    private TelephoneType telephoneType;

    @Ignore
    protected Telephone(Parcel in) {
        id = in.readInt();
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        dest.writeInt(id);
        dest.writeString(number);
        dest.writeValue(telephoneType);
    }
}
