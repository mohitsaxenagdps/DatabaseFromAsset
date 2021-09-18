package com.example.databasefromasset.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.databasefromasset.data.model.Student

@Database(entities = [Student::class], version = 2, exportSchema = false)
abstract class StudentDatabase : RoomDatabase() {
    abstract fun studentDao(): StudentDao
}