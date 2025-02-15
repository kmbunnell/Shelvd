package com.shelvd.di

import android.content.Context
import com.shelvd.data.api.OpenLibraryApiImpl
import com.shelvd.data.Util
import com.shelvd.data.api.ApiService
import com.shelvd.data.model.IsbnScanner
import com.shelvd.data.model.IsbnScannerImpl
import com.shelvd.data.repo.BookRepository
import com.shelvd.data.repo.DefaultBookRepository
import com.shelvd.domain.IsbnLookUpUseCase
import com.shelvd.domain.ScanBookUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import javax.inject.Qualifier
import javax.inject.Singleton


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
    fun providesScanBookUseCase(isbnScanner: IsbnScanner, isbnLookUpUseCase: IsbnLookUpUseCase)= ScanBookUseCase(
       isbnScanner, isbnLookUpUseCase
    )

    @Singleton
    @Provides
    fun providesIsbnScanner(@ApplicationContext appContext: Context) = IsbnScannerImpl(
        appContext
    )

    @Provides
    fun providesIsbnLookUpUseCase(bookRepository: BookRepository)= IsbnLookUpUseCase(bookRepository)

    @Singleton
    @Provides
    fun provideHttpClient(): HttpClient {
        return HttpClient(Android)
        {
            install(Logging){
                level = LogLevel.ALL
            }
            install(DefaultRequest){
                url(Util.BASE_URL)
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
            install(ContentNegotiation){
                json(Json{
                    ignoreUnknownKeys = true
                    isLenient = true

                })
            }
        }
    }

    @Singleton
    @Provides
    fun provideApiService(httpClient: HttpClient): ApiService = OpenLibraryApiImpl(httpClient)
}


@Module
@InstallIn(SingletonComponent::class)
abstract class ShelvdBindsModule{

    @Singleton
    @Binds
    abstract fun bindBookRepo(
        defaultBookRepository: DefaultBookRepository
    ):BookRepository

    @Singleton
    @Binds
    abstract fun bindIsbnScanner(
        isbnScannerImpl: IsbnScannerImpl
    ):IsbnScanner

}

