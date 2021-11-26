package com.nkuppan.weatherapp.domain.respository

import com.nkuppan.weatherapp.domain.model.Theme
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface ThemeRepository {

    /**
     * Storing the selected theme
     */
    fun saveTheme(theme: Theme, coroutineScope: CoroutineScope)

    /**
     * Storing the selected theme
     */
    fun applyTheme(coroutineScope: CoroutineScope, mainThreadDispatcher: CoroutineDispatcher)


    /**
     * Reading the selected theme
     */
    fun getSelectedTheme(): Flow<Theme>


    /**
     * Reading the list of theme available in the data store
     */
    fun getThemes(): List<Theme>
}