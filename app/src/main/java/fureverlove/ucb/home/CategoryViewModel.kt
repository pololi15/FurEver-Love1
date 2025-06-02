package fureverlove.ucb.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucb.data.mascota.IMascotaRepository
import com.ucb.domain.model.Mascota
//import com.ucb.domain.repository.FirestoreMascotaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    //private val repo: FirestoreMascotaRepository
    private val mascotaRepository: IMascotaRepository
) : ViewModel() {

    private val _mascotas = MutableStateFlow<List<Mascota>>(emptyList())
    val mascotas: StateFlow<List<Mascota>> = _mascotas

    private val _filteredPets = MutableStateFlow<List<Mascota>>(emptyList())
    val filteredPets: StateFlow<List<Mascota>> = _filteredPets

    init {
        cargarMascotas()
    }

    private fun cargarMascotas() {
        viewModelScope.launch {
            //_mascotas.value = repo.obtenerMascotas()
            _mascotas.value = mascotaRepository.obtenerMascotas()
        }
    }

    fun getPetsByCategory(category: String) {
        viewModelScope.launch {
            if (_mascotas.value.isEmpty()) {
                //_mascotas.value = repo.obtenerMascotas()
                _mascotas.value = mascotaRepository.obtenerMascotas()
            }
            _filteredPets.value = _mascotas.value.filter {
                it.especie.equals(category, ignoreCase = true)
            }
        }
    }
}
