package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class TopViewModel(
    private val practitionerRepository: PractitionerRepository,
    private val recentPractitionersRepository: RecentPractitionersRepository
) : ViewModel() {

    private val _recentPractitioners = MutableLiveData<List<RecentPractitioner>>()
    val recentPractitioners: LiveData<List<RecentPractitioner>> = _recentPractitioners

    fun loadRecentPractitioners() {
        viewModelScope.launch {
            val practitioners = recentPractitionersRepository.getRecentPractitioners()
            if (practitioners.isEmpty()) {
                // Load sample data if empty
                val samplePractitioners = listOf(
                    RecentPractitioner("1", "Emma", null),
                    RecentPractitioner("2", "Maya", null),
                    RecentPractitioner("3", "Sofia", null)
                )
                samplePractitioners.forEach { recentPractitionersRepository.saveRecentPractitioner(it) }
                _recentPractitioners.postValue(recentPractitionersRepository.getRecentPractitioners())
            } else {
                _recentPractitioners.postValue(practitioners)
            }
        }
    }

    fun addRecentPractitioner(practitioner: RecentPractitioner) {
        viewModelScope.launch {
            recentPractitionersRepository.saveRecentPractitioner(practitioner)
            // Reload the list to reflect the change
            loadRecentPractitioners()
        }
    }
}

class TopViewModelFactory(
    private val practitionerRepository: PractitionerRepository,
    private val recentPractitionersRepository: RecentPractitionersRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TopViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TopViewModel(practitionerRepository, recentPractitionersRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
