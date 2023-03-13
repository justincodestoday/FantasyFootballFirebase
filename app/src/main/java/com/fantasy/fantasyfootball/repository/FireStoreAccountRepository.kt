package com.fantasy.fantasyfootball.repository

import com.fantasy.fantasyfootball.data.model.Account
import com.google.firebase.firestore.CollectionReference

class FireStoreAccountRepository(private val ref: CollectionReference) {
    fun registerAccount(account: Account) {
        ref.add(account)
    }

    fun updateAccount(
        email: String,
        account: Account,
        callback: (status: Boolean) -> Unit
    ) {
        ref.whereEqualTo("email", email).get()
            .addOnCompleteListener { querySnapshot ->
                if (querySnapshot.isSuccessful) {
                    ref.document().set(account.copy(email = email))
                    callback(true)
                } else {
                    callback(false)
                }
            }
    }
}