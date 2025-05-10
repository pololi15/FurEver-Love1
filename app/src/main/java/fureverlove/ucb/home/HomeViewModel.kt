package fureverlove.ucb.home

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
class HomeViewModel @Inject constructor(
    private val repo: FirestoreMascotaRepository
) : ViewModel() {

    private val _mascotas = MutableStateFlow<List<Mascota>>(emptyList())
    val mascotas: StateFlow<List<Mascota>> = _mascotas

    init {
        cargarMascotas()
    }

    fun cargarMascotas() {
        viewModelScope.launch {
            _mascotas.value = repo.obtenerMascotas()
        }
    }
}
