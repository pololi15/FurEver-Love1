package fureverlove.ucb.mascota

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucb.domain.model.Mascota
import com.ucb.usecases.GetPopularMascotas
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.ucb.data.NetworkResult

@HiltViewModel
class MascotaViewModel@Inject constructor (private val getPopularMascotas: GetPopularMascotas): ViewModel() {

           sealed class MascotaUIState {
            object Loading: MascotaUIState()
            class Loaded(val list: List<Mascota>): MascotaUIState()
            class Error(val message: String): MascotaUIState()
          }
        private val _state = MutableStateFlow<MascotaUIState>(MascotaUIState.Loading)
        val state : StateFlow<MascotaUIState> = _state

        fun loadMascota() {
            _state.value = MascotaUIState.Loading
            viewModelScope.launch {
                val response = getPopularMascotas.invoke()
                when ( val result = response ) {
                    is NetworkResult.Error -> {
                        _state.value = MascotaUIState.Error(result.error)
                    }
                    is NetworkResult.Success -> {
                        _state.value = MascotaUIState.Loaded(result.data)
                    }
                }

            }

        }

}