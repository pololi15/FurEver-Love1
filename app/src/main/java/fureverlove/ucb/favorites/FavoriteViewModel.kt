package fureverlove.ucb.favorites

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.ucb.domain.model.Mascota
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class FavoriteViewModel @Inject constructor() : ViewModel() {

    private val _favoriteIds = mutableStateListOf<String>()
    val favoriteIds: List<String> get() = _favoriteIds

    fun toggleFavorite(id: String) {
        if (_favoriteIds.contains(id)) {
            _favoriteIds.remove(id)
        } else {
            _favoriteIds.add(id)
        }
    }

    fun isFavorite(id: String): Boolean = _favoriteIds.contains(id)
}
