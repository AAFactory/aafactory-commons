package com.bumptech.glide.samples.imgur

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class MainActivityModule {
    @ContributesAndroidInjector
    internal abstract fun contributeMainActivityInjector(): MainActivity
}
