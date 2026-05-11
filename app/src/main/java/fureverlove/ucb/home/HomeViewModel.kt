package fureverlove.ucb.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucb.domain.model.Mascota
import com.ucb.usecases.AddFavoritePet
import com.ucb.usecases.GetFavoritePets
import com.ucb.usecases.GetPets
import com.ucb.usecases.RemoveFavoritePet
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPets: GetPets,
    private val getFavoritePets: GetFavoritePets,
    private val addFavoritePet: AddFavoritePet,
    private val removeFavoritePet: RemoveFavoritePet
) : ViewModel() {

    private val _mascotas = MutableStateFlow<List<Mascota>>(emptyList())
    val mascotas: StateFlow<List<Mascota>> = _mascotas

    private val _favoritos = MutableStateFlow<List<String>>(emptyList()) // Solo ids
    val favoritos: StateFlow<List<String>> = _favoritos

    private val uid = FirebaseAuth.getInstance().currentUser?.uid

    init {
        cargarMascotas()
        cargarFavoritos()
    }

    fun cargarMascotas() {
        /*viewModelScope.launch {
            _mascotas.value = getPets.invoke()
        }*/
        viewModelScope.launch {
            try {
                val lista = getPets.invoke()
                Log.d("DEBUG_PETS", "Mascotas recibidas: ${lista.size}")
                _mascotas.value = lista
            } catch (e: Exception) {
                Log.e("DEBUG_PETS", "Error al obtener mascotas: ${e.message}")
            }
        }


    }

    fun cargarFavoritos() {
        uid?.let {
            viewModelScope.launch {
                val favoritosMascotas = getFavoritePets.invoke(it)
                _favoritos.value = favoritosMascotas.map { mascota -> mascota.id }
            }
        }
    }

    fun toggleFavorite(mascotaId: String) {
        val mascota = _mascotas.value.find { it.id == mascotaId } ?: return
        uid?.let { userId ->
            viewModelScope.launch {
                val isFavorite = _favoritos.value.contains(mascotaId)
                if (isFavorite) {
                    removeFavoritePet.invoke(userId, mascota)
                } else {
                    addFavoritePet.invoke(userId, mascota)
                }
                cargarFavoritos()
            }
        }
    }
}

