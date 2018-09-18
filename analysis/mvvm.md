# todo-mvvm-live-kotlin

```
│  LifecycleAppCompatActivity.kt
│  ScrollChildSwipeRefreshLayout.kt
│  SingleLiveEvent.kt
│  ViewModelFactory.kt
│
├─addedittask
│      AddEditTaskActivity.kt
│      AddEditTaskFragment.kt
│      AddEditTaskNavigator.kt
│      AddEditTaskViewModel.kt
│
├─data
│  │  Task.kt
│  │
│  └─source
│      │  TasksDataSource.kt
│      │  TasksRepository.kt
│      │
│      ├─local
│      │      TasksDbHelper.kt
│      │      TasksLocalDataSource.kt
│      │      TasksPersistenceContract.kt
│      │
│      └─remote
│              TasksRemoteDataSource.kt
│
├─statistics
│      StatisticsActivity.kt
│      StatisticsFragment.kt
│      StatisticsViewModel.kt
│
├─taskdetail
│      TaskDetailActivity.kt
│      TaskDetailFragment.kt
│      TaskDetailNavigator.kt
│      TaskDetailUserActionsListener.kt
│      TaskDetailViewModel.kt
│
├─tasks
│      TaskItemNavigator.kt
│      TaskItemUserActionsListener.kt
│      TasksActivity.kt
│      TasksAdapter.kt
│      TasksFilterType.kt
│      TasksFragment.kt
│      TasksListBindings.kt
│      TasksNavigator.kt
│      TasksViewModel.kt
│
└─util
        AppCompatActivityExt.kt
        EspressoIdlingResource.kt
        SimpleCountingIdlingResource.kt
        ViewExt.kt
```