package com.ucb.data.auth

import com.ucb.domain.model.AuthUser
import com.ucb.data.util.Result

interface IAuthRepository {
    suspend fun login(email: String, password: String): Result<AuthUser>
    suspend fun register(email: String, password: String): Result<AuthUser>
}
