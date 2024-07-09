package com.monsalud.pokepedia.di

import android.app.Application
import android.util.Log
import androidx.room.Room
import com.google.gson.Gson
import com.monsalud.pokepedia.data.LocalDataSource
import com.monsalud.pokepedia.data.PokepediaRepositoryImpl
import com.monsalud.pokepedia.data.RemoteDataSource
import com.monsalud.pokepedia.data.datasource.local.LocalDataSourceImpl
import com.monsalud.pokepedia.data.datasource.local.room.PokepediaDAO
import com.monsalud.pokepedia.data.datasource.local.room.PokepediaDatabase
import com.monsalud.pokepedia.data.datasource.remote.NetworkUtils
import com.monsalud.pokepedia.data.datasource.remote.RemoteDataSourceImpl
import com.monsalud.pokepedia.data.datasource.utils.EntityMappers
import com.monsalud.pokepedia.domain.PokepediaRepository
import com.monsalud.pokepedia.presentation.PokepediaViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.observer.ResponseObserver
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    val moduleInstance = AppModule()

    fun provideDatabase(application: Application): PokepediaDatabase {
        return Room.databaseBuilder(
            application,
            PokepediaDatabase::class.java,
            AppModule.POKEPEDIA_DATABASE
        ).build()
    }

    fun provideDao(database: PokepediaDatabase): PokepediaDAO {
        return database.pokepediaDao()
    }

    viewModel { PokepediaViewModel(get(), get()) }

    single { LocalDataSourceImpl(get(), get(), get()) } bind LocalDataSource::class
    single { RemoteDataSourceImpl(get()) } bind RemoteDataSource::class
    single { PokepediaRepositoryImpl(get(), get(), get(), get()) } bind PokepediaRepository::class
    single { provideDatabase(androidApplication()) } bind PokepediaDatabase::class
    single { provideDao(get()) }

    single(qualifier = null) { moduleInstance.ktorClient() }
    single { Gson() }
    single { EntityMappers() }
    single { NetworkUtils() }

}

class AppModule {
    fun ktorClient() = HttpClient(Android) {
        install(JsonFeature) {
            serializer = KotlinxSerializer(
                Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                    encodeDefaults = true
                }
            )
        }
        install(ResponseObserver) {
            onResponse { response ->
                Log.i("KOIN", "${response.status.value}")
            }
        }
        engine {
            connectTimeout = TIMEOUT
            socketTimeout = TIMEOUT
        }
    }

    companion object {
        const val POKEPEDIA_DATABASE = "pokepedia_database"
        const val TIMEOUT = 10_000
    }
}