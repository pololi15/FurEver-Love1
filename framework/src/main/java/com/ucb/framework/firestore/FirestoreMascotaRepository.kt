package com.ucb.framework.firestore

import com.google.firebase.firestore.FirebaseFirestore
import com.ucb.domain.model.Mascota
import kotlinx.coroutines.tasks.await

class FirestoreMascotaRepository {

    private val db = FirebaseFirestore.getInstance()
    private val mascotasRef = db.collection("mascotas")

    // Metodo para guardar una nueva mascota
    suspend fun agregarMascota(mascota: Mascota) {
        mascotasRef.add(mascota).await()
    }

    //Metodo para obtener mascotas
    suspend fun obtenerMascotas(): List<Mascota> {
        return mascotasRef.get().await().mapNotNull {
            it.toObject(Mascota::class.java)?.copy(id = it.id)
        }
    }
}
