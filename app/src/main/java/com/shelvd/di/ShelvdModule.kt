package com.shelvd.di

import android.content.Context
import com.shelvd.data.repo.BookRepository
import com.shelvd.data.repo.DefaultBookRepository
import com.shelvd.domain.ScanBookUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier


@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class DefaultDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IoDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class MainDispatcher

@Module
@InstallIn(SingletonComponent::class)
object ShelvdModule {
    @Provides
    @DefaultDispatcher
    fun provideDefaultDispatcher() : CoroutineDispatcher = Dispatchers.Default


    @Provides
    @IoDispatcher
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO


    @Provides
    @MainDispatcher
    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Provides
    fun providesScanBookUseCase(@ApplicationContext appContext: Context)= ScanBookUseCase(appContext)
}


@Module
@InstallIn(SingletonComponent::class)
abstract class ShelvdBindsModule{

    @Binds
    abstract fun bindBookRepo(
        defaultBookRepository: DefaultBookRepository
    ):BookRepository
}

