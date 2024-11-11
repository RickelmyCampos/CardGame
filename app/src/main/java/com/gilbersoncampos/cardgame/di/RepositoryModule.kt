package com.gilbersoncampos.cardgame.di

import com.gilbersoncampos.cardgame.data.repository.BlackJackGameRepository
import com.gilbersoncampos.cardgame.data.repository.BlackJackGameRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindBlackJackGameRepository(blackJackGameRepository: BlackJackGameRepositoryImpl): BlackJackGameRepository
}