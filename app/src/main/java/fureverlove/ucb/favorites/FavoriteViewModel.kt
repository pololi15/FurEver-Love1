package fureverlove.ucb.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucb.domain.model.Mascota
import com.ucb.usecases.AddFavoritePet
import com.ucb.usecases.RemoveFavoritePet
import com.ucb.usecases.GetFavoritePets
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.google.firebase.auth.FirebaseAuth

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val addFavoritePet: AddFavoritePet,
    private val removeFavoritePet: RemoveFavoritePet,
    private val getFavoritePets: GetFavoritePets
) : ViewModel() {

    private val _favoritePets = MutableStateFlow<List<Mascota>>(emptyList())
    val favoritePets: StateFlow<List<Mascota>> = _favoritePets

    private val uid: String? get() = FirebaseAuth.getInstance().currentUser?.uid

    fun loadFavorites() {
        uid?.let { userId ->
            viewModelScope.launch {
                _favoritePets.value = getFavoritePets(userId)
            }
        }
    }

    fun toggleFavorite(mascota: Mascota) {
        uid?.let { userId ->
            viewModelScope.launch {
                val isFavorite = _favoritePets.value.any { it.id == mascota.id }
                if (isFavorite) {
                    removeFavoritePet(userId, mascota)
                } else {
                    addFavoritePet(userId, mascota)
                }
                loadFavorites()
            }
        }
    }
}
