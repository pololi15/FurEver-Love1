package com.ucb.usecases

import com.ucb.data.auth.IAuthRepository
import com.ucb.domain.model.AuthUser
import com.ucb.data.util.Result

class RegisterUser(private val repository: IAuthRepository) {
    suspend fun invoke(email: String, password: String): Result<AuthUser> =
        repository.register(email, password)
}