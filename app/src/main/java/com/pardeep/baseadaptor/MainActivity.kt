package com.pardeep.baseadaptor

import android.app.Dialog
import android.os.Bundle
import android.provider.SyncStateContract.Helpers.update
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.pardeep.baseadaptor.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    var binding : ActivityMainBinding? = null
    var student_List = arrayListOf<Student>()
    var listAdapter = ListAdapter(student_List)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

       student_List.add(Student("Ram","python",22))
       student_List.add(Student("Jame","c++",50))
       student_List.add(Student("Thakur","Dsa",12))
       student_List.add(Student("Thakur","Dsa",12))

        binding?.floatButton?.setOnClickListener{
            Dialog(this).apply {
                setContentView(R.layout.custom_dialog)
                window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                var button = findViewById<Button>(R.id.cd_button)
                var name = findViewById<EditText>(R.id.cd_name)
                var rollNumber = findViewById<EditText>(R.id.cd_rollNumber)
                var Subject = findViewById<EditText>(R.id.cd_Subject)

                button?.setOnClickListener {
                    if (name.text.trim().isNullOrEmpty()){
                        name.error = "enter name"
                    }
                    else if (rollNumber.text.trim().isNullOrEmpty()){
                        rollNumber.error = "enter rollNumber"
                    }
                    else if (Subject.text.trim().isNullOrEmpty()){
                        Subject.error = "enter subject name"
                    }else
                    {
                        val name_data = name.text.toString()
                        val subject_data = Subject.text.toString()
                        val roll_number = rollNumber.text.toString()

                        addData(name_data,subject_data,roll_number)

                        Toast.makeText(this@MainActivity,"button is preesed", Toast.LENGTH_SHORT).show()
                        dismiss()
                    }
                }
            }.show()
        }


        binding?.listView?.adapter = listAdapter

         // listview update
        binding?.listView?.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(this, "$position $id", Toast.LENGTH_SHORT).show()
            AlertDialog.Builder(this@MainActivity).apply {
                setTitle("Tittle")
                setPositiveButton("Update"){dialog,which ->

                    
                     update(data = position, data1 = "hello" , data2 = "kkk" , data3 = "88",)

                }
            }.show()

        }

        //listview delete

        binding?.listView?.setOnItemLongClickListener { parent, view, position, id ->
            AlertDialog.Builder(this@MainActivity).apply {
                setTitle("Tittle")
                setPositiveButton("delete"){dialog,which ->
                    deleteitem(position)
                }
            }.show()


            return@setOnItemLongClickListener true
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun update(data : Int ,data1: String, data2: String, data3: String) {
        student_List.set(data , Student(name = data1, subject = data2, rollNumber = data3.toInt()))
        listAdapter.notifyDataSetChanged()

    }


    private fun deleteitem(data : Int) {
        student_List.removeAt(data)

        listAdapter.notifyDataSetChanged()
    }

    fun addData(data1: String, data2: String, data3: String) {
        student_List.add(Student(name = data1, subject = data2  , rollNumber = data3.toInt()))
        listAdapter.notifyDataSetChanged()
    }
}