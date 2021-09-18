package com.example.databasefromasset.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students_table")
data class Student(

    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "student_name")
    val name: String,

    @ColumnInfo(name = "student_email")
    val email: String

)