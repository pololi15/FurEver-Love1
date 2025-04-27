package fureverlove.ucb.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucb.domain.model.AuthUser
import com.ucb.usecases.LoginUser
import com.ucb.usecases.RegisterUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.ucb.data.util.Result

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUser: LoginUser,
    private val registerUser: RegisterUser
) : ViewModel() {

    sealed class AuthState {
        object Idle : AuthState()
        object Loading : AuthState()
        data class Success(val user: AuthUser) : AuthState()
        data class Error(val message: String) : AuthState()
    }

    private val _state = MutableStateFlow<AuthState>(AuthState.Idle)
    val state: StateFlow<AuthState> = _state

    fun login(email: String, password: String) {
        _state.value = AuthState.Loading
        viewModelScope.launch {
            when (val result = loginUser.invoke(email, password)) {
                is Result.Success -> _state.value = AuthState.Success(result.data)
                is Result.Error -> _state.value = AuthState.Error(result.message)
            }
        }
    }

    fun register(email: String, password: String) {
        _state.value = AuthState.Loading
        viewModelScope.launch {
            when (val result = registerUser.invoke(email, password)) {
                is Result.Success -> _state.value = AuthState.Success(result.data)
                is Result.Error -> _state.value = AuthState.Error(result.message)
            }
        }
    }

    fun reset() {
        _state.value = AuthState.Idle
    }
}
