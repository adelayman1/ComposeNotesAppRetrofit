package com.example.noteappcompose.data.di.modules

import android.content.SharedPreferences
import com.example.noteappcompose.data.source.remote.endPoints.NotesApiService
import com.example.noteappcompose.data.source.remote.endPoints.UserApiService
import com.example.noteappcompose.data.utilities.Constants.BASE_HTTP_URL
import com.example.noteappcompose.data.utilities.Constants.USER_TOKEN_SHARED_PREFERENCES_KEY
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.Logging
import io.ktor.client.features.websocket.WebSockets
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_HTTP_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideUserApiService(retrofit: Retrofit): UserApiService {
        return retrofit.create(UserApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideNotesApiService(retrofit: Retrofit): NotesApiService {
        return retrofit.create(NotesApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        interceptor: HttpLoggingInterceptor,
        prefs: SharedPreferences
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor { chain ->
                var request = chain.request()
                if (request.header("No-Authentication") == null) {
                    val authToken: String = prefs.getString(USER_TOKEN_SHARED_PREFERENCES_KEY, null) ?: "guest"
                    request = request.newBuilder()
                        .addHeader("Authorization", authToken)
                        .build();
                }
                chain.proceed(request)
            }
            .addInterceptor(interceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .build();
    }

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(CIO) {
            install(Logging)
            install(WebSockets)
            install(JsonFeature) {
                serializer = KotlinxSerializer()
            }
        }
    }
}