package fureverlove.ucb.di

import com.ucb.data.auth.IAuthRepository
import com.ucb.framework.auth.FirebaseAuthRepository
import com.ucb.data.mascota.IMascotaRepository
import com.ucb.framework.firestore.FirestoreMascotaRepository
import com.ucb.usecases.AddFavoritePet
import com.ucb.usecases.GetFavoritePets
import com.ucb.usecases.GetPet
import com.ucb.usecases.GetPets
import com.ucb.usecases.SavePet
import com.ucb.usecases.LoginUser
import com.ucb.usecases.RegisterUser
import com.ucb.usecases.RemoveFavoritePet
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAuthRepository(): IAuthRepository = FirebaseAuthRepository()

    @Provides
    @Singleton
    fun provideLoginUser(repo: IAuthRepository): LoginUser = LoginUser(repo)

    @Provides
    @Singleton
    fun provideRegisterUser(repo: IAuthRepository): RegisterUser = RegisterUser(repo)

    @Provides
    @Singleton
    fun provideMascotaRepository(): IMascotaRepository = FirestoreMascotaRepository()

    @Provides
    @Singleton
    fun provideGetPets(repo: IMascotaRepository): GetPets = GetPets(repo)

    @Provides
    @Singleton
    fun provideGetPet(repo: IMascotaRepository): GetPet = GetPet(repo)

    @Provides
    @Singleton
    fun provideSavePet(repo: IMascotaRepository): SavePet = SavePet(repo)

    @Provides
    @Singleton
    fun provideAddFavoritePet(repo: IMascotaRepository): AddFavoritePet = AddFavoritePet(repo)

    @Provides
    @Singleton
    fun provideRemoveFavoritePet(repo: IMascotaRepository): RemoveFavoritePet = RemoveFavoritePet(repo)

    @Provides
    @Singleton
    fun provideGetFavoritePets(repo: IMascotaRepository): GetFavoritePets = GetFavoritePets(repo)
}
