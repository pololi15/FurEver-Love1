package com.ucb.framework.auth

import com.google.firebase.auth.FirebaseAuth
import com.ucb.data.auth.IAuthRepository
import com.ucb.domain.model.AuthUser
import com.ucb.data.util.Result
import kotlinx.coroutines.tasks.await

class FirebaseAuthRepository : IAuthRepository {

    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override suspend fun login(email: String, password: String): Result<AuthUser> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val user = result.user ?: return Result.Error("Usuario no encontrado")
            Result.Success(AuthUser(user.uid, user.email))
        } catch (e: Exception) {
            Result.Error(e.message ?: "Error al iniciar sesión")
        }
    }

    override suspend fun register(email: String, password: String): Result<AuthUser> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user ?: return Result.Error("No se pudo registrar")
            Result.Success(AuthUser(user.uid, user.email))
        } catch (e: Exception) {
            Result.Error(e.message ?: "Error al registrar usuario")
        }
    }
}

