package com.example.notepad.Database;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.notepad.Modles.Notes;

import java.util.List;

@Dao
public interface MainDAO {
    @Insert(onConflict = REPLACE)
    void insert(Notes notes);

    @Query("SELECT  * FROM notes ORDER BY id DESC")   //*means all item from notes table
    List<Notes> getAll();                              // DESC for newly updated should be on top

    @Query("UPDATE notes SET title = :title , notes = :notes WHERE  ID= :id")
    void update( int id , String title , String notes);

    @Delete
    void delete(Notes notes);
@Query("UPDATE notes SET pinned = :pin WHERE ID= :id")
    void pin(int id , boolean pin);
}
