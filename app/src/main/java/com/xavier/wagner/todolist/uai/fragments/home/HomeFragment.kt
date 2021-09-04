package com.xavier.wagner.todolist.uai.fragments.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.xavier.wagner.todolist.databinding.HomeFragmentBinding
import com.xavier.wagner.todolist.datasource.TaskDataSource
import com.xavier.wagner.todolist.uai.CreateTaskActivity

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel

    private lateinit var binding: HomeFragmentBinding

    private val adapter by lazy { TaskListAdapter() }

    private val REQUEST_CREATE_TASK = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        initRecyclerTasks()
        insertListeners()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun insertListeners() {
        binding.btnAddTask.setOnClickListener {
            startActivityForResult(Intent(requireActivity(), CreateTaskActivity::class.java), REQUEST_CREATE_TASK)
        }

        adapter.listenerEdit = {
            startActivityForResult(Intent(requireActivity(), CreateTaskActivity::class.java).putExtra(TaskDataSource.TASK_ID, it.id), REQUEST_CREATE_TASK)
        }
        adapter.listenerDelete = {
            TaskDataSource.removeTask(it)
            updateListTask()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_CREATE_TASK && resultCode == AppCompatActivity.RESULT_OK) updateListTask()
    }

    private fun initRecyclerTasks(){
        binding.recyclerTasks.adapter = adapter
        adapter.submitList(TaskDataSource.getList())
    }

    override fun onStart() {
        super.onStart()
        updateListTask()
    }

    private fun updateListTask(){
        val list = TaskDataSource.getList()

        binding.includeLayout.emptyState.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE

        adapter.submitList(TaskDataSource.getList().reversed())
    }


}