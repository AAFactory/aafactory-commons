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

import android.arch.lifecycle.LifecycleFragment
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import com.example.android.architecture.blueprints.todoapp.mvvm.util.setupSnackbar
import io.github.aafactory.sample.R
import io.github.aafactory.sample.databinding.TodomvvmTaskdetailFragBinding

/**
 * Main UI for the task detail screen.
 */
class TaskDetailFragment : LifecycleFragment() {

    private lateinit var viewDataBinding: TodomvvmTaskdetailFragBinding

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupFab()
        view?.setupSnackbar(this, viewDataBinding.viewmodel!!.snackbarMessage, Snackbar.LENGTH_LONG)
    }

    private fun setupFab() {
        with(activity!!.findViewById<View>(R.id.fab_edit_task)) {
            setOnClickListener { viewDataBinding.viewmodel!!.editTask() }
        }
    }

    override fun onResume() {
        super.onResume()
        viewDataBinding.viewmodel!!.start(arguments!!.getString(ARGUMENT_TASK_ID))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.todomvvm_taskdetail_frag, container, false)
        viewDataBinding = TodomvvmTaskdetailFragBinding.bind(view).apply {
            viewmodel = (activity as TaskDetailActivity).obtainViewModel()
            listener = object : TaskDetailUserActionsListener {
                override fun onCompleteChanged(v: View) {
                    viewmodel!!.setCompleted((v as CheckBox).isChecked)
                }
            }
        }
        setHasOptionsMenu(true)
        return view
    }

    override fun onOptionsItemSelected(item: MenuItem) =
            when (item.itemId) {
                R.id.menu_delete -> {
                    viewDataBinding.viewmodel!!.deleteTask()
                    true
                }
                else -> false
            }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.todomvvm_taskdetail_fragment_menu, menu)
    }

    companion object {

        const val ARGUMENT_TASK_ID = "TASK_ID"
        const val REQUEST_EDIT_TASK = 1

        fun newInstance(taskId: String) = TaskDetailFragment().apply {
            arguments = Bundle().apply {
                putString(ARGUMENT_TASK_ID, taskId)
            }
        }

    }
}
