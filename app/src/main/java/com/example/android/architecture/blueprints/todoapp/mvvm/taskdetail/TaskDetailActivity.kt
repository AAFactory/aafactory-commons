/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.architecture.blueprints.todoapp.mvvm.taskdetail

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import com.example.android.architecture.blueprints.todoapp.mvvm.LifecycleAppCompatActivity
import com.example.android.architecture.blueprints.todoapp.mvvm.addedittask.AddEditTaskActivity
import com.example.android.architecture.blueprints.todoapp.mvvm.addedittask.AddEditTaskFragment
import com.example.android.architecture.blueprints.todoapp.mvvm.taskdetail.TaskDetailFragment.Companion.REQUEST_EDIT_TASK
import com.example.android.architecture.blueprints.todoapp.mvvm.util.ADD_EDIT_RESULT_OK
import com.example.android.architecture.blueprints.todoapp.mvvm.util.DELETE_RESULT_OK
import com.example.android.architecture.blueprints.todoapp.mvvm.util.EDIT_RESULT_OK
import com.example.android.architecture.blueprints.todoapp.mvvm.util.obtainViewModel
import com.example.android.architecture.blueprints.todoapp.mvvm.util.replaceFragmentInActivity
import com.example.android.architecture.blueprints.todoapp.mvvm.util.setupActionBar
import io.github.aafactory.sample.R

/**
 * Displays task details screen.
 */
class TaskDetailActivity : LifecycleAppCompatActivity(), TaskDetailNavigator {

    private lateinit var taskViewModel: TaskDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.todomvvm_taskdetail_act)

        setupActionBar(R.id.toolbar) {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        replaceFragmentInActivity(findOrCreateViewFragment(), R.id.contentFrame)

        taskViewModel = obtainViewModel()

        subscribeToNavigationChanges(taskViewModel)
    }

    private fun findOrCreateViewFragment() =
            supportFragmentManager.findFragmentById(R.id.contentFrame) ?:
                    TaskDetailFragment.newInstance(intent.getStringExtra(EXTRA_TASK_ID))

    private fun subscribeToNavigationChanges(viewModel: TaskDetailViewModel) {
        // The activity observes the navigation commands in the ViewModel
        val activity = this@TaskDetailActivity
        viewModel.run {
            editTaskCommand.observe(activity,
                    Observer { activity.onStartEditTask() })
            deleteTaskCommand.observe(activity,
                    Observer { activity.onTaskDeleted() })
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_EDIT_TASK) {
            // If the task was edited successfully, go back to the list.
            if (resultCode == ADD_EDIT_RESULT_OK) {
                // If the result comes from the add/edit screen, it's an edit.
                setResult(EDIT_RESULT_OK)
                finish()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onTaskDeleted() {
        setResult(DELETE_RESULT_OK)
        // If the task was deleted successfully, go back to the list.
        finish()
    }

    override fun onStartEditTask() {
        val taskId = intent.getStringExtra(EXTRA_TASK_ID)
        val intent = Intent(this, AddEditTaskActivity::class.java).apply {
            putExtra(AddEditTaskFragment.ARGUMENT_EDIT_TASK_ID, taskId)
        }
        startActivityForResult(intent, REQUEST_EDIT_TASK)
    }

    fun obtainViewModel(): TaskDetailViewModel = obtainViewModel(TaskDetailViewModel::class.java)

    companion object {

        const val EXTRA_TASK_ID = "TASK_ID"

    }

}
