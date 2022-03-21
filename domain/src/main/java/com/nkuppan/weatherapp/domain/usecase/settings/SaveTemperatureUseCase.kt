package com.nkuppan.weatherapp.domain.usecase.settings

import com.nkuppan.weatherapp.domain.model.Resource
import com.nkuppan.weatherapp.domain.model.Temperature
import com.nkuppan.weatherapp.domain.respository.SettingsRepository

class SaveTemperatureUseCase(private val repository: SettingsRepository) {

    suspend operator fun invoke(temperature: Temperature): Resource<Boolean> {
        return Resource.Success(repository.saveTemperature(temperature))
    }
}