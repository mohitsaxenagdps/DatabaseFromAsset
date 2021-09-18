package com.example.databasefromasset.domain.repository

import androidx.lifecycle.LiveData
import com.example.databasefromasset.data.model.Student

interface StudentRepository {

    fun getAllStudents(): LiveData<List<Student>>

    suspend fun insertStudent(student: Student): Long

    suspend fun updateStudent(student: Student): Int

    suspend fun deleteStudent(student: Student): Int

    suspend fun deleteAllStudents(): Int

}