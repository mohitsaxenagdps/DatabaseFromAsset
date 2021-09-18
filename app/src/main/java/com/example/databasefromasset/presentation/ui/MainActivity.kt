package com.example.databasefromasset.presentation.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.databasefromasset.R
import com.example.databasefromasset.data.model.Student
import com.example.databasefromasset.databinding.ActivityMainBinding
import com.example.databasefromasset.presentation.adapter.RecyclerViewAdapter
import com.example.databasefromasset.presentation.viewmodel.MainActivityViewModel
import com.example.databasefromasset.presentation.viewmodel_factory.MainActivityViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel

    @Inject
    lateinit var viewModelFactory: MainActivityViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainActivityViewModel::class.java]
        binding.myViewModel = viewModel
        binding.lifecycleOwner = this

        val adapter = RecyclerViewAdapter { selectedStudent: Student ->
            listItemClicked(
                selectedStudent
            )
        }
        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter
        }

        viewModel.students.observe(this, {
            adapter.setList(it)
        })

        viewModel.mToastMessage.observe(this, {
            it.getContentIfNotHandled()?.let { msg->
                Toast.makeText(this@MainActivity, msg, Toast.LENGTH_LONG).show()

            }
        })

    }

    private fun listItemClicked(selectedStudent: Student) {
        viewModel.listItemClick(selectedStudent)
    }

//    private fun copyDatabase() {
//        try {
//            myDbHelper.createDataBase()
//        } catch (ioe: IOException) {
//            throw Error("Unable to create database")
//        }
//        try {
//            myDbHelper.openDataBase()
//        } catch (e: SQLException) {
//            throw e
//        }
//        Toast.makeText(this, "Successfully Imported", Toast.LENGTH_SHORT).show()
//
//        val c = myDbHelper.query("Student")
//        if (c.moveToFirst()) {
//            do {
//                Toast.makeText(
//                    this@MainActivity,
//                    """
//                        _id: ${c.getString(0)}
//                        name: ${c.getString(1)}
//                        email: ${c.getString(2)}
//                        """.trimIndent(),
//                    Toast.LENGTH_LONG
//                ).show()
//
//            } while (c.moveToNext())
//        }
//    }
}