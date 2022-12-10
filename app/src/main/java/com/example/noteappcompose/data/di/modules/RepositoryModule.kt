package com.example.noteappcompose.data.di.modules

import com.example.noteappcompose.data.repositories.NoteRepositoryImpl
import com.example.noteappcompose.data.repositories.UserRepositoryImpl
import com.example.noteappcompose.domain.repositories.NoteRepository
import com.example.noteappcompose.domain.repositories.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [DatabaseModule::class])
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Singleton
    @Provides
    fun provideNoteRepository(noteRepositoryImpl: NoteRepositoryImpl): NoteRepository {
        return noteRepositoryImpl
    }
    @Singleton
    @Provides
    fun provideUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository {
        return userRepositoryImpl
    }
}