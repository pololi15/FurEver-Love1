package fureverlove.ucb.pet
/*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucb.domain.model.Mascota
import com.ucb.framework.firestore.FirestoreMascotaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PetViewModel @Inject constructor(
    mascota: Mascota,
    private val repo: FirestoreMascotaRepository
) : ViewModel() {

    private val _mascota = MutableStateFlow(mascota)
    val mascota = _mascota
    val id = _mascota.value.id
    init {
        cargarMascota()
    }

    fun cargarMascota() {
        viewModelScope.launch {
            _mascota.value = repo.obtenerMascota(id) ?: Mascota()
        }
    }

}*/