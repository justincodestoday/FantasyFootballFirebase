package com.fantasy.fantasyfootball.service

import android.net.Uri
import android.util.Log
import com.google.firebase.storage.FirebaseStorage

object ImageStorageService {
    private val ref = FirebaseStorage.getInstance().getReference("images/")

    fun getImageDownloadUrl(fileName: String, callback: (url: String?) -> Unit) {
        ref.child(fileName).downloadUrl.addOnSuccessListener {
            callback(it.toString())
        }.addOnFailureListener {
            callback(null)
        }
    }

    fun addImage(uri: Uri, fileName: String, callback: (status: Boolean) -> Unit) {
        ref.child(fileName).putFile(uri)
            .addOnSuccessListener {
                callback(true)
            }
            .addOnFailureListener {
                callback(false)
            }
    }

    fun getImageUri(fileName: String, callback: (uri: Uri) -> Unit) {
        ref.child(fileName).downloadUrl
            .addOnSuccessListener {
                callback(it)
            }
            .addOnFailureListener {
                Log.d("debugging", it.toString())
            }
    }
}