package com.fantasy.fantasyfootball.repository

import com.fantasy.fantasyfootball.data.UserDao
import com.fantasy.fantasyfootball.data.model.User

class UserRepository(private val userDao: UserDao) {
//    suspend fun getUsers(str: String): List<User> {
//        if (str == "") {
//            return wordDao.getWords()
//        }
//        return wordDao.getWordsBySearch(str)
//    }
//
//    // This suspend function returns the suspend function to get a unit of data from the data set by the ID.
//    suspend fun getWordById(id: Long): Word? {
//        return wordDao.getWordById(id)
//    }
//
//    // This suspend function returns the suspend function to insert a new unit of data to the data set.
//    suspend fun addWord(word: Word) {
//        wordDao.insert(word)
//    }
//
//    // This suspend function returns the suspend function to update a unit of data from the data set by the ID.
//    suspend fun changeWordStatus(id: Long, status: Boolean) {
//        wordDao.updateStatusById(id, status)
//    }
//
//    // This suspend function returns the suspend function to replace an existing unit of data to the data set.
//    suspend fun editWord(id: Long, word: Word) {
//        wordDao.insert(word.copy(id = id))
//    }
//
//    // This suspend function returns the suspend function to delete a unit of data from the data set by the ID.
//    suspend fun deleteWord(id: Long) {
//        wordDao.delete(id)
//    }
}