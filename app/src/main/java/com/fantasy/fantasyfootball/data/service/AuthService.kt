package com.fantasy.fantasyfootball.data.service

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference

class AuthService(private val auth: FirebaseAuth,
                  private val ref: CollectionReference
) {
}