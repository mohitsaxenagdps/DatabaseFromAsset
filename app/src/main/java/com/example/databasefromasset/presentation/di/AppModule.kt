package com.example.databasefromasset.presentation.di

import android.content.Context
import androidx.room.Room
import com.example.databasefromasset.data.db.DatabaseHelper
import com.example.databasefromasset.data.db.StudentDao
import com.example.databasefromasset.data.db.StudentDatabase
import com.example.databasefromasset.data.repository.StudentRepositoryImpl
import com.example.databasefromasset.domain.repository.StudentRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideDbHelper(@ApplicationContext context: Context): DatabaseHelper {
        return DatabaseHelper(context)
    }

    @Provides
    @Singleton
    fun provideStudentDatabase(@ApplicationContext context: Context): StudentDatabase {
        return Room.databaseBuilder(context, StudentDatabase::class.java, "student_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideMovieDao(studentDatabase: StudentDatabase): StudentDao {
        return studentDatabase.studentDao()
    }

    @Provides
    @Singleton
    fun provideStudentRepo(studentRepositoryImpl: StudentRepositoryImpl): StudentRepository {
        return studentRepositoryImpl
    }

}