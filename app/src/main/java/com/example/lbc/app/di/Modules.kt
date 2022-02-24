package com.example.lbc.app.di

import android.app.Application
import com.example.lbc.BuildConfig
import com.example.lbc.common.AppDispatchers
import com.example.lbc.common.Resource
import com.example.lbc.data.api.Interceptor
import com.example.lbc.data.api.Service

import com.example.lbc.data.repository.ServiceImpl
import com.example.lbc.domain.model.ProductPresentation
import com.example.lbc.domain.providers.ProductProvider
import com.example.lbc.domain.usecase.MainUseCase
import com.example.lbc.domain.usecase.ProductUseCase
import com.example.lbc.domain.usecase.ProductUseCaseImpl
import com.example.lbc.presentation.viewmodels.ProductViewModel
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val apiModule = module {
    fun provideUserApi(retrofit: Retrofit): Service {
        return retrofit.create(Service::class.java)
    }
    single { provideUserApi(get()) }
    single<ProductProvider> { ServiceImpl(get()) }
    single<ProductUseCase> { ProductUseCaseImpl(get()) }
    single { AppDispatchers(computation = Dispatchers.Default, io = Dispatchers.IO, main = Dispatchers.Main.immediate) }
    viewModel { ProductViewModel(get(), get()) }
}

val netModule = module {
    fun provideCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    fun provideInterceptor() = Interceptor()

    fun provideHttpClient(cache: Cache, interceptor: Interceptor): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .cache(cache)

        return okHttpClientBuilder.build()
    }

    fun provideGson(): Gson {
        return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()
    }

    fun provideRetrofit(factory: Gson, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(GsonConverterFactory.create(factory))

            .client(client)
            .build()
    }
    single { provideCache(androidApplication()) }
    single { provideInterceptor() }
    single { provideHttpClient(get(), get()) }
    single { provideGson() }
    single { provideRetrofit(get(), get()) }
}

