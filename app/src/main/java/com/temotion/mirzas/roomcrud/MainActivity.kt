package com.temotion.mirzas.roomcrud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.temotion.mirzas.roomcrud.Database.AppDatabase
import com.temotion.mirzas.roomcrud.Models.Students
import com.temotion.mirzas.roomcrud.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var binding : ActivityMainBinding
    private lateinit var appDB : AppDatabase

    /* steps of work with Room
    * 1. Cerate Model according to requirnments
    * 2. Create DAO Class where CRUD operations will be Defined
    * 3. Create Singleton Application Class for DB
    * 4. Implement them to Views.*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        appDB = AppDatabase.getDatabase(this)

        binding.dbaddbutton.setOnClickListener{
            writeData()
        }

        binding.readdatabutton.setOnClickListener{
            readData()
        }

    }

    private fun writeData() {
         val fname = binding.fnameedit.text.toString()
        val lname = binding.lnameedit.text.toString()
        val rollname = binding.rolledit.text.toString()
        Log.d(TAG, "writeData: ")
        lateinit var studentInsert : Students
        if (fname.isNotEmpty() && lname.isNotEmpty() && rollname.isNotEmpty()){
            studentInsert = Students(null,fname,lname,rollname.toInt())
        }
        GlobalScope.launch ( Dispatchers.IO ){
            Log.d(TAG, "writeData: scope")
            appDB.studentDao().insert(studentInsert)
        }

        binding.fnameedit.text.clear()
        binding.lnameedit.text.clear()
        binding.rolledit.text.clear()

        Toast.makeText(this,"Insert Successfully",Toast.LENGTH_LONG).show()
    }

    private fun readData() {
        val rollNo = binding.editFindbyid.text.toString()

        if(rollNo.isNotEmpty()){
            lateinit var student : Students
            GlobalScope.launch {
                student = appDB.studentDao().findByroll(rollNo.toInt())
                DisplaySelectedStudent(student)
            }

        }
    }

    private suspend fun DisplaySelectedStudent(student : Students) {
       withContext(Dispatchers.Main){                      // Running the operation on the Main thread
           binding.fname.text= student.firstname.toString()
           binding.lname.text= student.lastname.toString()
           binding.rollno.text = student.rollNo.toString()
       }
    }


}