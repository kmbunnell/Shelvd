package com.shelvd.di

import com.shelvd.data.repo.BookRepository
import com.shelvd.data.repo.DefaultBookRepository
import com.shelvd.data.repo.DefaultShelfRepository
import com.shelvd.data.repo.ShelfRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class ShelvdModule{
    @Binds
    abstract fun bindShelfRepo(
        defaultShelfRepository: DefaultShelfRepository
    ):ShelfRepository

    @Binds
    abstract fun bindBookRepo(
        defaultBookRepository: DefaultBookRepository
    ):BookRepository

}

