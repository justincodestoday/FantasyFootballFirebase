package com.fantasy.fantasyfootball.di

import com.fantasy.fantasyfootball.data.repository.MatchRepositoryImpl
import com.fantasy.fantasyfootball.data.repository.MatchesRepositoryImpl
import com.fantasy.fantasyfootball.data.repository.PlayerRepositoryImpl
import com.fantasy.fantasyfootball.data.repository.UserRepositoryImpl
import com.fantasy.fantasyfootball.data.service.RetrofitService
import com.fantasy.fantasyfootball.domain.repository.MatchRepository
import com.fantasy.fantasyfootball.domain.repository.MatchesRepository
import com.fantasy.fantasyfootball.domain.repository.PlayerRepository
import com.fantasy.fantasyfootball.domain.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MyAppDependency {
    @Provides
    @Singleton
    fun getFireStore(): FirebaseFirestore {
        return Firebase.firestore
    }

    @Provides
    @Singleton
    fun getFireAuth(): FirebaseAuth {
        return Firebase.auth
    }

    @Provides
    @Singleton
    fun getPlayerRepository(db: FirebaseFirestore): PlayerRepository {
        return PlayerRepositoryImpl(db.collection("players"))
    }

    @Provides
    @Singleton
    fun getMatchRepository(db: FirebaseFirestore): MatchRepository {
        return MatchRepositoryImpl(db.collection("matches"))
    }

    @Provides
    @Singleton
    fun getAuthRepository(auth: FirebaseAuth, db: FirebaseFirestore): UserRepository {
        return UserRepositoryImpl(auth, db.collection("users"))
    }

    @Provides
    @Singleton
    fun getMatchesRepository(): MatchesRepository {
        return MatchesRepositoryImpl(RetrofitService.api)
    }
}