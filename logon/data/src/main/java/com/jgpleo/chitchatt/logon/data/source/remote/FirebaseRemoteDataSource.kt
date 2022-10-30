package com.jgpleo.chitchatt.logon.data.source.remote

import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.jgpleo.chitchatt.logon.data.source.FailureState
import com.jgpleo.chitchatt.logon.data.source.RemoteDataSource
import com.jgpleo.chitchatt.logon.data.source.Response
import com.jgpleo.chitchatt.logon.data.source.State
import com.jgpleo.chitchatt.logon.data.source.remote.mapper.RemoteMapper
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseRemoteDataSource @Inject constructor() : RemoteDataSource {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    override suspend fun login(email: String, pass: String): State<Response> {
        return errorHandlerTemplate {
            val result = auth.signInWithEmailAndPassword(email, pass).await()
            result.user?.let { firebaseUser ->
                val userData = RemoteMapper.map(firebaseUser)
                State.success(Response(userData))
            } ?: State.failure(FailureState.InvalidUserData)
        }
    }

    override suspend fun createUser(email: String, pass: String): State<Response> {
        return errorHandlerTemplate {
            val result = auth.createUserWithEmailAndPassword(email, pass).await()
            result.user?.let { firebaseUser ->
                val userData = RemoteMapper.map(firebaseUser)
                State.success(Response(userData))
            } ?: State.failure(FailureState.InvalidUserData)
        }
    }

    private suspend fun errorHandlerTemplate(
        action: suspend () -> State<Response>
    ): State<Response> {
        return try {
            action()
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            State.failure(FailureState.InvalidUser)
        } catch (e: FirebaseTooManyRequestsException) {
            State.failure(FailureState.TooManyRequest)
        } catch (e: FirebaseAuthUserCollisionException) {
            State.failure(FailureState.UserAlreadyExists)
        } catch (e: Exception) {
            State.failure(FailureState.Unknown)
        }
    }

    override suspend fun rememberPassword(email: String): State<Unit> {
        auth.sendPasswordResetEmail(email).await()
        return State.success(Unit)
    }

}