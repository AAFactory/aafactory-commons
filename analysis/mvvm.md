# todo-mvvm-live-kotlin

```
├─java
│  └─com
│      └─example
│          └─android
│              └─architecture
│                  └─blueprints
│                      └─todoapp
│                          └─mvvm
│                              │  LifecycleAppCompatActivity.kt
│                              │  ScrollChildSwipeRefreshLayout.kt
│                              │  SingleLiveEvent.kt
│                              │  ViewModelFactory.kt
│                              │  
│                              ├─addedittask
│                              │      AddEditTaskActivity.kt
│                              │      AddEditTaskFragment.kt
│                              │      AddEditTaskNavigator.kt
│                              │      AddEditTaskViewModel.kt
│                              │      
│                              ├─data
│                              │  │  Task.kt
│                              │  │  
│                              │  └─source
│                              │      │  TasksDataSource.kt
│                              │      │  TasksRepository.kt
│                              │      │  
│                              │      ├─local
│                              │      │      TasksDbHelper.kt
│                              │      │      TasksLocalDataSource.kt
│                              │      │      TasksPersistenceContract.kt
│                              │      │      
│                              │      └─remote
│                              │              TasksRemoteDataSource.kt
│                              │              
│                              ├─statistics
│                              │      StatisticsActivity.kt
│                              │      StatisticsFragment.kt
│                              │      StatisticsViewModel.kt
│                              │      
│                              ├─taskdetail
│                              │      TaskDetailActivity.kt
│                              │      TaskDetailFragment.kt
│                              │      TaskDetailNavigator.kt
│                              │      TaskDetailUserActionsListener.kt
│                              │      TaskDetailViewModel.kt
│                              │      
│                              ├─tasks
│                              │      TaskItemNavigator.kt
│                              │      TaskItemUserActionsListener.kt
│                              │      TasksActivity.kt
│                              │      TasksAdapter.kt
│                              │      TasksFilterType.kt
│                              │      TasksFragment.kt
│                              │      TasksListBindings.kt
│                              │      TasksNavigator.kt
│                              │      TasksViewModel.kt
│                              │      
│                              └─util
│                                      AppCompatActivityExt.kt
│                                      EspressoIdlingResource.kt
│                                      SimpleCountingIdlingResource.kt
│                                      ViewExt.kt
│                                      
│              
└─res
    │      
    ├─layout
    │      todomvvm_addtask_act.xml
    │      todomvvm_addtask_frag.xml
    │      todomvvm_nav_header.xml
    │      todomvvm_statistics_act.xml
    │      todomvvm_statistics_frag.xml
    │      todomvvm_taskdetail_act.xml
    │      todomvvm_taskdetail_frag.xml
    │      todomvvm_tasks_act.xml
    │      todomvvm_tasks_frag.xml
    │      todomvvm_task_item.xml
    │      
    ├─menu
    │      todomvvm_drawer_actions.xml
    │      todomvvm_filter_tasks.xml
    │      todomvvm_taskdetail_fragment_menu.xml
    │      todomvvm_tasks_fragment_menu.xml

```
