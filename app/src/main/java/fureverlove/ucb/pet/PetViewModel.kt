package fureverlove.ucb.pet

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucb.data.mascota.IMascotaRepository
import com.ucb.domain.model.Mascota
import com.ucb.framework.firestore.FirestoreMascotaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PetViewModel @Inject constructor(
    private val repository: IMascotaRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _mascota = MutableStateFlow(Mascota())
    val mascota = _mascota

    //val id = _mascota.value.id
    init {
        val id = savedStateHandle.get<String>("petId")
        if (id != null) {
            cargarMascota(id)
        }
    }

    fun cargarMascota(id: String) {
        viewModelScope.launch {
            _mascota.value = repository.obtenerMascota(id) ?: Mascota()
        }
    }
}


// interesante, savedStateHandle se usa para guardar y recuperar datos en el estado guardado