package fureverlove.ucb.di

import com.ucb.data.auth.IAuthRepository
import com.ucb.framework.auth.FirebaseAuthRepository
import com.ucb.data.mascota.IMascotaRepository
import com.ucb.usecases.LoginUser
import com.ucb.usecases.RegisterUser
import dagger.Binds
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

}
