package com.example.databasefromasset.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.databasefromasset.R
import com.example.databasefromasset.data.model.Student
import com.example.databasefromasset.databinding.StudentLayoutBinding

class RecyclerViewAdapter(private val clickListener: (Student) -> Unit) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private val studentList = ArrayList<Student>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<StudentLayoutBinding>(
            LayoutInflater.from(parent.context),
            R.layout.student_layout,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(studentList[position], clickListener)
    }

    override fun getItemCount() = studentList.size

    fun setList(subscribers: List<Student>) {
        studentList.clear()
        studentList.addAll(subscribers)
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: StudentLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(student: Student, clickListener: (Student) -> Unit) {
            binding.nameTextView.text = student.name
            binding.emailTextView.text = student.email
            binding.cardView.setOnClickListener {
                clickListener(student)
            }
        }

    }

}