package com.xavier.wagner.todolist.uai

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.xavier.wagner.todolist.databinding.ActivityCreateTaskBinding
import com.xavier.wagner.todolist.datasource.TaskDataSource
import com.xavier.wagner.todolist.extensions.format
import com.xavier.wagner.todolist.extensions.formatHour
import com.xavier.wagner.todolist.model.Task
import java.util.*

class CreateTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateTaskBinding
    private var task: Task = Task(0, "","","")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addListeners()

        setValuesDefault()

        val extras = intent.extras
        extras?.let {
            val taskId = it.getInt(TaskDataSource.TASK_ID)

            TaskDataSource.findById(taskId)?.let {
                binding.editTitle.setText(it.title)
                binding.textDate.text = it.date
                binding.textHour.text = it.hour
            }
        }
    }

    private fun setValuesDefault() {
        val date = Date()
        binding.textDate.text = date.format()
        binding.textHour.text = date.formatHour()
    }

    private fun addListeners() {
        binding.textDate.setOnClickListener {
            val datePiker = MaterialDatePicker.Builder.datePicker().build()
            datePiker.addOnPositiveButtonClickListener {
                val timeZone = TimeZone.getDefault()
                val offset = timeZone.getOffset(Date().time) * -1
                binding.textDate.text = Date(it + offset).format()
            }

            datePiker.show(supportFragmentManager, "DATE_PIKER_TAG")

        }

        binding.textHour.setOnClickListener {
            val timePiker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build()
            timePiker.addOnPositiveButtonClickListener {
                val hour = if(timePiker.hour in 0..9) "0${timePiker.hour}" else timePiker.hour
                val minute = if(timePiker.minute in 0..9) "0${timePiker.minute}" else timePiker.minute

                binding.textHour.text = "${hour}:${minute}"
            }

            timePiker.show(supportFragmentManager, "DATE_PIKER_TAG")

        }

        binding.imgCancel.setOnClickListener {
            dialogAlertDescartTask()
        }
        binding.imgSave.setOnClickListener {
            val task = Task(
                title = binding.editTitle.text.toString(),
                date = binding.textDate.text.toString(),
                hour = binding.textHour.text.toString(),
                id = intent.getIntExtra(TaskDataSource.TASK_ID, 0)
            )
            TaskDataSource.insertTask(task)
            Toast.makeText(this, "Tarefa criada!", Toast.LENGTH_LONG).show()
            setResult(RESULT_OK)
            finish()
        }
    }

    fun dialogAlertDescartTask(){
        val dialog = AlertDialog.Builder(this)
            .setMessage("Descartar tarefa?") // Specifying a listener allows you to take an action before dismissing the dialog.
            .setPositiveButton("Manter"){_, _ ->

            }
            .setNeutralButton("Descartar"){_, _ ->
                finish()
            }
            .show()
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).isAllCaps = false
        dialog.getButton(DialogInterface.BUTTON_NEUTRAL).isAllCaps = false
    }
}