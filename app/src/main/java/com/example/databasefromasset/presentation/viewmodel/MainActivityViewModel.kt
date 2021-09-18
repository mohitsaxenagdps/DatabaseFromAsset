package com.example.databasefromasset.presentation.viewmodel

import android.database.SQLException
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.databasefromasset.common.Event
import com.example.databasefromasset.data.db.DatabaseHelper
import com.example.databasefromasset.data.model.Student
import com.example.databasefromasset.domain.use_case.*
import kotlinx.coroutines.launch
import java.io.IOException

class MainActivityViewModel(
    getAllStudentsUseCase: GetAllStudentsUseCase,
    private val insertStudentUseCase: InsertStudentUseCase,
    private val updateStudentUseCase: UpdateStudentUseCase,
    private val deleteAllStudentsUseCase: DeleteAllStudentsUseCase,
    private val deleteStudentUseCase: DeleteStudentUseCase,
    private val myDbHelper: DatabaseHelper
) : ViewModel() {

    val name = MutableLiveData<String?>()
    val email = MutableLiveData<String?>()
    val saveOrUpdate = MutableLiveData<String>()
    val clearAllOrDelete = MutableLiveData<String>()
    private val toastMessage = MutableLiveData<Event<String>>()
    val mToastMessage: LiveData<Event<String>>
        get() = toastMessage
    val students = getAllStudentsUseCase.execute()
    private var isUpdate = false
    private var isDelete = false
    private var studentId: Int = 0

    init {
        saveOrUpdate.value = "Save"
        clearAllOrDelete.value = "Clear All"
        copyDatabase()
    }

    fun insertSubscriberOrUpdate() {
        if (name.value == null || name.value.toString().trim().isEmpty()) {
            toastMessage.value = Event("Please enter student's name")
        } else if (email.value == null || email.value.toString().trim().isEmpty()) {
            toastMessage.value = Event("Please enter student's email")
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.value!!).matches()) {
            toastMessage.value = Event("Please enter correct email address")
        } else {
            if (!isUpdate) {
                viewModelScope.launch {
                    val rowId = insertStudentUseCase.execute(
                        Student(
                            0,
                            name.value.toString(),
                            email.value.toString()
                        )
                    )
                    if (rowId > -1) {
                        toastMessage.value = Event("Student inserted successfully at Id $rowId")
                    } else {
                        toastMessage.value = Event("Error Occurred")
                    }
                    name.value = null
                    email.value = null
                }

            } else {
                viewModelScope.launch {
                    val noOfRows = updateStudentUseCase.execute(
                        Student(
                            studentId,
                            name.value.toString(),
                            email.value.toString()
                        )
                    )
                    if (noOfRows > 0) {
                        name.value = null
                        email.value = null
                        isDelete = false
                        isUpdate = false
                        saveOrUpdate.value = "Save"
                        clearAllOrDelete.value = "Clear All"
                        toastMessage.value = Event("$noOfRows Student updated successfully")
                    } else {
                        toastMessage.value = Event("Error Occurred")
                    }
                }
            }
        }
    }

    fun deleteAllOrDelete() {
        if (!isDelete) {
            viewModelScope.launch {
                val noOfRows = deleteAllStudentsUseCase.execute()
                if (noOfRows > 0) {
                    toastMessage.value = Event("$noOfRows Students deleted successfully")
                } else {
                    toastMessage.value = Event("Error Occurred")
                }
            }
        } else {
            viewModelScope.launch {
                val noOfRows = deleteStudentUseCase.execute(
                    Student(
                        studentId,
                        name.value.toString(),
                        email.value.toString()
                    )
                )
                if (noOfRows > 0) {
                    name.value = null
                    email.value = null
                    isDelete = false
                    isUpdate = false
                    saveOrUpdate.value = "Save"
                    clearAllOrDelete.value = "ClearAll"
                    toastMessage.value = Event("$noOfRows Students deleted successfully")
                } else {
                    toastMessage.value = Event("Error Occurred")
                }
            }
        }
    }

    private fun copyDatabase() {
        try {
            myDbHelper.createDataBase()
        } catch (e: IOException) {
            throw Error("Unable to create database")
        }
        try {
            myDbHelper.openDataBase()
        } catch (e: SQLException) {
            throw e
        }
        Log.i("MyTag", "Database Successfully imported")

        val c = myDbHelper.query("students")
        viewModelScope.launch {
            if (c.moveToFirst()) {
                do {
                    insertStudentUseCase.execute(
                        Student(
                            c.getInt(0),
                            c.getString(1),
                            c.getString(2)
                        )
                    )
                } while (c.moveToNext())
            }
        }
    }

    fun listItemClick(student: Student) {
        isDelete = true
        isUpdate = true
        saveOrUpdate.value = "Update"
        clearAllOrDelete.value = "Delete"
        name.value = student.name
        email.value = student.email
        studentId = student.id
    }

}