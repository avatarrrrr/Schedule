package br.com.alura.schedule.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.alura.schedule.models.Student;

@Dao
public interface RoomStudentDAO {
    @Insert
    Long save(Student avatar);

    @Query("SELECT * FROM student")
    List<Student> getStudents();

    @Delete
    void delete(Student student);

    @Update
    void edit(Student student);
}
