package com.fantasy.fantasyfootball.repository

import com.fantasy.fantasyfootball.data.model.Account
import com.google.firebase.firestore.CollectionReference

class FireStoreAccountRepository(private val ref: CollectionReference) {
    suspend fun registerAccount(account: Account) {
        ref.add(account)
    }
}