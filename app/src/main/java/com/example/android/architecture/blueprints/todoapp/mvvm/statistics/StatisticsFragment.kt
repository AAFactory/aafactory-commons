/*
 * Copyright 2017, The Android Open Source Project
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
package com.example.android.architecture.blueprints.todoapp.mvvm.statistics

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import io.github.aafactory.sample.R
import io.github.aafactory.sample.databinding.TodomvvmStatisticsFragBinding

/**
 * Main UI for the statistics screen.
 */
class StatisticsFragment : Fragment() {

    private lateinit var viewDataBinding: TodomvvmStatisticsFragBinding

    private lateinit var statisticsViewModel: StatisticsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.todomvvm_statistics_frag, container,
                false)
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        statisticsViewModel = (activity as StatisticsActivity).obtainViewModel()
        viewDataBinding.stats = statisticsViewModel
    }

    override fun onResume() {
        super.onResume()
        statisticsViewModel.start()
    }

    companion object {
        fun newInstance() = StatisticsFragment()
    }
}
