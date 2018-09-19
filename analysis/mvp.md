# todo-mvp-kotlin
 
```
│      
├─java
│  └─com
│      └─example
│          └─android
│              └─architecture
│                  └─blueprints
│                      └─todoapp
│                          └─mvp
│                              │  BasePresenter.kt
│                              │  BaseView.kt
│                              │  
│                              ├─addedittask
│                              │      AddEditTaskActivity.kt
│                              │      AddEditTaskContract.kt
│                              │      AddEditTaskFragment.kt
│                              │      AddEditTaskPresenter.kt
│                              │      
│                              ├─data
│                              │  │  Task.kt
│                              │  │  
│                              │  └─source
│                              │      │  TasksDataSource.kt
│                              │      │  TasksRepository.kt
│                              │      │  
│                              │      └─local
│                              │              TasksDao.kt
│                              │              TasksLocalDataSource.kt
│                              │              ToDoDatabase.kt
│                              │              
│                              ├─statistics
│                              │      StatisticsActivity.kt
│                              │      StatisticsContract.kt
│                              │      StatisticsFragment.kt
│                              │      StatisticsPresenter.kt
│                              │      
│                              ├─taskdetail
│                              │      TaskDetailActivity.kt
│                              │      TaskDetailContract.kt
│                              │      TaskDetailFragment.kt
│                              │      TaskDetailPresenter.kt
│                              │      
│                              ├─tasks
│                              │      ScrollChildSwipeRefreshLayout.kt
│                              │      TasksActivity.kt
│                              │      TasksContract.kt
│                              │      TasksFilterType.kt
│                              │      TasksFragment.kt
│                              │      TasksPresenter.kt
│                              │      
│                              └─util
│                                      AppCompatActivityExt.kt
│                                      AppExecutors.kt
│                                      DiskIOThreadExecutor.kt
│                                      EspressoIdlingResource.kt
│                                      SimpleCountingIdlingResource.kt
│                                      ViewExt.kt

│                                      
│              
└─res
    │      
    ├─layout
    │      todomvp_addtask_act.xml
    │      todomvp_addtask_frag.xml
    │      todomvp_nav_header.xml
    │      todomvp_statistics_act.xml
    │      todomvp_statistics_frag.xml
    │      todomvp_taskdetail_act.xml
    │      todomvp_taskdetail_frag.xml
    │      todomvp_tasks_act.xml
    │      todomvp_tasks_frag.xml
    │      todomvp_task_item.xml

    │      
    ├─menu
    │      todomvp_drawer_actions.xml
    │      todomvp_filter_tasks.xml
    │      todomvp_taskdetail_fragment_menu.xml
    │      todomvp_tasks_fragment_menu.xml


            

```
