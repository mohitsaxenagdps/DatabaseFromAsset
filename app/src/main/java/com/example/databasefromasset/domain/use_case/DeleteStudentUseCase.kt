package com.example.databasefromasset.domain.use_case

import com.example.databasefromasset.data.model.Student
import com.example.databasefromasset.domain.repository.StudentRepository
import javax.inject.Inject

class DeleteStudentUseCase @Inject constructor(private val repository: StudentRepository) {
    suspend fun execute(student: Student) = repository.deleteStudent(student)
}