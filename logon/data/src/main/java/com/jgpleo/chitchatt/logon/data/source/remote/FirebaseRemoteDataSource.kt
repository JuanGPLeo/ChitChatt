package com.jgpleo.chitchatt.logon.data.source.remote

import com.google.firebase.auth.FirebaseAuth
import com.jgpleo.chitchatt.logon.data.source.RemoteDataSource
import com.jgpleo.chitchatt.logon.data.source.Response
import com.jgpleo.chitchatt.logon.data.source.State
import com.jgpleo.chitchatt.logon.data.source.remote.mapper.RemoteMapper
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseRemoteDataSource @Inject constructor() : RemoteDataSource {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    override suspend fun createUser(email: String, pass: String): State<Response> {
        val result = auth.createUserWithEmailAndPassword(email, pass).await()
        return result.user?.let { firebaseUser ->
            val userData = RemoteMapper.map(firebaseUser)
            State.success(Response(userData))
        } ?: State.failure("Unable to retrieve user data")
    }

    override suspend fun login(email: String, pass: String): State<Response> {
        val result = auth.signInWithEmailAndPassword(email, pass).await()
        return result.user?.let { firebaseUser ->
            val userData = RemoteMapper.map(firebaseUser)
            State.success(Response(userData))
        } ?: State.failure("Unable to retrieve user data")
    }

    override suspend fun rememberPassword(email: String): State<Unit> {
        auth.sendPasswordResetEmail(email).await()
        return State.success(Unit)
    }

}