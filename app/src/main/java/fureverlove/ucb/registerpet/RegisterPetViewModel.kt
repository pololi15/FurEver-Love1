package fureverlove.ucb.registerpet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucb.domain.model.Mascota
import com.ucb.framework.firestore.FirestoreMascotaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterPetViewModel @Inject constructor(
    private val repository: FirestoreMascotaRepository
) : ViewModel() {

    private val _mensaje = MutableStateFlow("")
    val mensaje: StateFlow<String> = _mensaje

    fun guardarMascota(mascota: Mascota) {
        viewModelScope.launch {
            try {
                repository.agregarMascota(mascota)
                _mensaje.value = "Mascota registrada con éxito"
            } catch (e: Exception) {
                _mensaje.value = "Error: ${e.message}"
            }
        }
    }
}
