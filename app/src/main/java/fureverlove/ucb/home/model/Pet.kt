package fureverlove.ucb.home.model


data class Pet(
    val id: Int,
    val name: String,
    val gender: String,
    val imageUrl: String,
    val category: String // "canes" o "gatos"
)
