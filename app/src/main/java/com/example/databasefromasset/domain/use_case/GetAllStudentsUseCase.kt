package com.example.databasefromasset.domain.use_case

import com.example.databasefromasset.domain.repository.StudentRepository
import javax.inject.Inject

class GetAllStudentsUseCase @Inject constructor(private val repository: StudentRepository) {
    fun execute() = repository.getAllStudents()
}