package com.fantasy.fantasyfootball.di

import com.fantasy.fantasyfootball.data.repository.MatchRepositoryImpl
import com.fantasy.fantasyfootball.data.repository.PlayerRepositoryImpl
import com.fantasy.fantasyfootball.data.repository.UserRepositoryImpl
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
    fun getFireStorePlayerRepository(db: FirebaseFirestore): PlayerRepositoryImpl {
        return PlayerRepositoryImpl(db.collection("players"))
    }

    @Provides
    @Singleton
    fun getFireStoreMatchRepository(db: FirebaseFirestore): MatchRepositoryImpl {
        return MatchRepositoryImpl(db.collection("matches"))
    }

    @Provides
    @Singleton
    fun getAuthRepository(auth: FirebaseAuth, db: FirebaseFirestore): UserRepositoryImpl {
        return UserRepositoryImpl(auth, db.collection("users"))
    }
}