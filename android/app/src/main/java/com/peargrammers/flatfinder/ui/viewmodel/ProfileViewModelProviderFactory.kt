package com.peargrammers.flatfinder.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.peargrammers.flatfinder.repository.ProfileRepository

class ProfileViewModelProviderFactory(
    val profileRepository: ProfileRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProfileViewModel(profileRepository) as T
    }
}