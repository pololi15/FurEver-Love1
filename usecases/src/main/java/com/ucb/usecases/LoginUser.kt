package com.ucb.usecases

import com.ucb.data.auth.IAuthRepository
import com.ucb.domain.model.AuthUser
import com.ucb.data.util.Result

class LoginUser(private val repository: IAuthRepository) {
    suspend fun invoke(email: String, password: String): Result<AuthUser> =
        repository.login(email, password)
}
