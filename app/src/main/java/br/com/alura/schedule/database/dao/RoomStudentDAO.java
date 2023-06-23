package br.com.alura.schedule.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import br.com.alura.schedule.models.Student;

@Dao
public interface RoomStudentDAO {
    @Insert
    void save(Student avatar);

    @Query("SELECT * FROM student")
    List<Student> getStudents();

    @Delete
    void delete(Student student);
}
