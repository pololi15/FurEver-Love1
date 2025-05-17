package fureverlove.ucb.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import fureverlove.ucb.home.model.Pet

class CategoryViewModel : ViewModel() {

    private val _pets = MutableStateFlow<List<Pet>>(listOf(
        Pet(1, "Bidó", "♂", "https://www.purina.es/sites/default/files/styles/ttt_image_510/public/2024-02/sitesdefaultfilesstylessquare_medium_440x440public2022-09german20shepherd.jpg?itok=V8iRVUvy", "canes"),
        Pet(2, "Thor", "♂", "https://upload.wikimedia.org/wikipedia/commons/8/8a/Golden_Retriever_9-year_old.jpg", "canes"),
        Pet(3, "Mel", "♀", "https://www.purina.es/sites/default/files/styles/ttt_image_510/public/2024-02/sitesdefaultfilesstylessquare_medium_440x440public2022-07Chihuahua-Smooth-Coat.jpg?itok=L2PWsV9_", "canes"),
        Pet(4, "Tico", "♂", "https://www.adopta.mx/wp-content/uploads/2013/05/criollo.jpg", "canes"),
        Pet(5, "Michi", "♀", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRr2uP5h2rgkTWj7nVdNIBGefhNu88_eRFEjw&s", "gatos"),
        Pet(6, "Luna", "♀", "https://sp-ao.shortpixel.ai/client/to_auto,q_glossy,ret_img,w_800,h_420/https://hospitalveterinario.cr/wp-content/uploads/2019/02/gatos-adorables-1-1024x538.jpg", "gatos")
    ))

    fun getPetsByCategory(category: String): StateFlow<List<Pet>> {
        return MutableStateFlow(_pets.value.filter { it.category == category })
    }
}
