package com.fantasy.fantasyfootball.di

import com.fantasy.fantasyfootball.repository.*
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

    //For Firebase
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
    fun getFireStorePlayerRepository(db: FirebaseFirestore): PlayerRepository {
        return FireStorePlayerRepository(db.collection("players"))
    }

    @Provides
    @Singleton
    fun getFireStoreTeamRepository(db: FirebaseFirestore): TeamRepository {
        return FireStoreTeamRepository(db.collection("teams"))
    }

    @Provides
    @Singleton
    fun getFireStoreMatchRepository(db: FirebaseFirestore): MatchRepository {
        return FireStoreMatchRepository(db.collection("matches"))
    }

    @Provides
    @Singleton
    fun getAuthRepository(auth: FirebaseAuth, db: FirebaseFirestore): FireStoreUserRepository {
        return FireStoreUserRepository(auth, db.collection("users"))
    }
}