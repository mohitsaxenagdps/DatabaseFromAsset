package com.example.databasefromasset.presentation.viewmodel_factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.databasefromasset.data.db.DatabaseHelper
import com.example.databasefromasset.domain.use_case.*
import com.example.databasefromasset.presentation.viewmodel.MainActivityViewModel
import javax.inject.Inject

class MainActivityViewModelFactory @Inject constructor(
    private val getAllStudentsUseCase: GetAllStudentsUseCase,
    private val insertStudentUseCase: InsertStudentUseCase,
    private val updateStudentUseCase: UpdateStudentUseCase,
    private val deleteAllStudentsUseCase: DeleteAllStudentsUseCase,
    private val deleteStudentUseCase: DeleteStudentUseCase,
    private val myDbHelper: DatabaseHelper
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainActivityViewModel(
            getAllStudentsUseCase,
            insertStudentUseCase,
            updateStudentUseCase,
            deleteAllStudentsUseCase,
            deleteStudentUseCase,
            myDbHelper
        ) as T
    }
}