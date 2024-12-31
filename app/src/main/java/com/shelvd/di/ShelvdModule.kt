package com.shelvd.di

import com.shelvd.data.repo.BookRepository
import com.shelvd.data.repo.DefaultBookRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier


@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class DefaultDispatcher

@Module
@InstallIn(SingletonComponent::class)
object ShelvdModule {
    @Provides
    @DefaultDispatcher
    fun provideDefaultDispatcher() : CoroutineDispatcher = Dispatchers.Default
}


@Module
@InstallIn(SingletonComponent::class)
abstract class ShelvdBindsModule{

    @Binds
    abstract fun bindBookRepo(
        defaultBookRepository: DefaultBookRepository
    ):BookRepository
}

