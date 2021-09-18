package com.example.databasefromasset.data.db

import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream


class DatabaseHelper(private val myContext: Context) :
    SQLiteOpenHelper(myContext, DB_NAME, null, 1) {
    private var dbPath: String? = null
    private var myDataBase: SQLiteDatabase? = null

    companion object {
        private const val DB_NAME = "myDatabase.db"
    }

    init {
        dbPath = myContext.getDatabasePath(DB_NAME).absolutePath
        Log.i("MyTag", dbPath.toString())
    }

    @Throws(IOException::class)
    fun createDataBase() {
        val dbExist = checkDataBase()
        if(!dbExist) {
            this.readableDatabase
            try {
                copyDataBase()
            } catch (e: IOException) {
                throw Error("Error copying database")
            }
        }
    }

    private fun checkDataBase(): Boolean {
        var checkDB: SQLiteDatabase? = null
        try {
            val myPath = dbPath + DB_NAME
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY)
        } catch (e: SQLiteException) {
        }
        checkDB?.close()
        return checkDB != null
    }

    @Throws(IOException::class)
    private fun copyDataBase() {
        val myInput = myContext.assets.open(DB_NAME)
        val outFileName = dbPath + DB_NAME
        val myOutput: OutputStream = FileOutputStream(outFileName)
        val buffer = ByteArray(10)
        var length: Int
        while (myInput.read(buffer).also { length = it } > 0) {
            myOutput.write(buffer, 0, length)
        }
        myOutput.flush()
        myOutput.close()
        myInput.close()
    }

    @Throws(SQLException::class)
    fun openDataBase() {
        val myPath = dbPath + DB_NAME
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY)
    }

    @Synchronized
    override fun close() {
        if (myDataBase != null) myDataBase!!.close()
        super.close()
    }

    override fun onCreate(db: SQLiteDatabase) {}
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (newVersion > oldVersion) try {
            copyDataBase()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun query(s: String): Cursor {
        return myDataBase!!.query(
            s,
            null,
            null,
            null,
            null,
            null,
            null
        )
    }
}