package com.example.databasefromasset.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.databasefromasset.data.model.Student

@Dao
interface StudentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudent(student: Student): Long

    @Update
    suspend fun update(student: Student): Int

    @Delete
    suspend fun delete(student: Student): Int

    @Query("DELETE FROM students_table")
    suspend fun deleteAll(): Int

    @Query("Select * From students_table")
    fun getAllStudent(): LiveData<List<Student>>

}