package com.xavier.wagner.todolist.uai.fragments.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.xavier.wagner.todolist.R
import com.xavier.wagner.todolist.databinding.ItemTaskBinding
import com.xavier.wagner.todolist.model.Task

class TaskListAdapter : ListAdapter<Task, TaskListAdapter.TaskViewHolder>(DiffCallback()) {

    private lateinit var context: Context
    var listenerEdit : (Task) -> Unit = {}
    var listenerDelete : (Task) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(context)
        val binding = ItemTaskBinding.inflate(inflater, parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TaskViewHolder(
        private val binding: ItemTaskBinding
        ): RecyclerView.ViewHolder(binding.root){
            fun bind(item: Task){
                binding.tvTitle.text = item.title
                binding.tvDate.text = "${item.date}      ${item.hour}"
                binding.ivMore.setOnClickListener {
                    showPopup(item)
                }
            }

            private fun showPopup(item: Task){
                val ivMore = binding.ivMore
                val popupMenu = PopupMenu(ivMore.context, ivMore)
                popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
                popupMenu.setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.action_edit -> listenerEdit(item)
                        R.id.action_delete -> listenerDelete(item)
                    }
                    return@setOnMenuItemClickListener true
                }
                popupMenu.show()
            }
        }
}

class DiffCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task) = oldItem == newItem
    override fun areContentsTheSame(oldItem: Task, newItem: Task) = oldItem.id == newItem.id

}
