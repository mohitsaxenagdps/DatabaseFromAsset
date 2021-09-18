package com.example.databasefromasset.data.repository

import com.example.databasefromasset.data.db.StudentDao
import com.example.databasefromasset.data.model.Student
import com.example.databasefromasset.domain.repository.StudentRepository
import javax.inject.Inject

class StudentRepositoryImpl @Inject constructor(private val dao: StudentDao) : StudentRepository {

    override fun getAllStudents() = dao.getAllStudent()

    override suspend fun insertStudent(student: Student): Long {
        return dao.insertStudent(student)
    }

    override suspend fun updateStudent(student: Student): Int {
        return dao.update(student)
    }

    override suspend fun deleteStudent(student: Student): Int {
        return dao.delete(student)
    }

    override suspend fun deleteAllStudents(): Int {
        return dao.deleteAll()
    }
}