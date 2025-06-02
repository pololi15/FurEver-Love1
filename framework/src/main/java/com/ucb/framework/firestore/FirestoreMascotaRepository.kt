package com.ucb.framework.firestore

import com.google.firebase.firestore.FirebaseFirestore
import com.ucb.domain.model.Mascota
import kotlinx.coroutines.tasks.await
import com.ucb.data.mascota.IMascotaRepository
import javax.inject.Inject

class FirestoreMascotaRepository @Inject constructor(): IMascotaRepository {

    private val db = FirebaseFirestore.getInstance()
    private val mascotasRef = db.collection("mascotas")

    // Metodo para guardar una nueva mascota
    override suspend fun agregarMascota(mascota: Mascota) {
        mascotasRef.add(mascota).await()
    }

    //Metodo para obtener mascotas
    override suspend fun obtenerMascotas(): List<Mascota> {
        return mascotasRef.get().await().mapNotNull {
            it.toObject(Mascota::class.java)?.copy(id = it.id)
        }
    }
    // Metodo para obtener una mascota por ID
     override suspend fun obtenerMascota(id: String): Mascota? {
        return mascotasRef.document(id).get().await().toObject(Mascota::class.java)
    }

}
