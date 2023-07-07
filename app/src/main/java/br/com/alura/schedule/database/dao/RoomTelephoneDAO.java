package br.com.alura.schedule.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import br.com.alura.schedule.models.Telephone;

@Dao
public interface RoomTelephoneDAO {
    @Query(
            "SELECT * FROM Telephone telephone WHERE telephone.studentIdentifier = :studentIdentifier LIMIT 1"
    )
    Telephone getFirstTelephoneFromStudent(int studentIdentifier);

    @Insert
    void save(Telephone... telephones);

    @Query("SELECT * FROM Telephone telephone WHERE telephone.studentIdentifier = :identifier")
    List<Telephone> getTelephonesFromStudent(int identifier);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void update(Telephone... telephones);

    @Delete
    void delete(Telephone... telephones);
}
