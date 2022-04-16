package com.example.moviesapptermpaper.di

import androidx.fragment.app.Fragment
import com.example.moviesapptermpaper.ui.search.SearchContract
import com.example.moviesapptermpaper.ui.search.SearchFragment
import com.example.moviesapptermpaper.ui.search.SearchPresenter
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@InstallIn(FragmentComponent::class)
@Module
abstract class SearchModule {

    @Binds
    abstract fun bindFragment(fragment: SearchFragment): SearchContract.View

    @Binds
    abstract fun bindPresenter(presenter: SearchPresenter): SearchContract.Presenter
}

@InstallIn(FragmentComponent::class)
@Module
object SearchFragmentModule {

    @Provides
    fun provideFragment(fragment: Fragment): SearchFragment = fragment as SearchFragment
}