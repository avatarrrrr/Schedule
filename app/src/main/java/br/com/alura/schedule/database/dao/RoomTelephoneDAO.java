package br.com.alura.schedule.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import br.com.alura.schedule.models.Telephone;

@Dao
public interface RoomTelephoneDAO {
    @Query(
            "SELECT * FROM Telephone telephone WHERE telephone.studentIdentifier = :studentIdentifier LIMIT 1"
    )
    Telephone getFirstTelephoneFromStudent(int studentIdentifier);
}
