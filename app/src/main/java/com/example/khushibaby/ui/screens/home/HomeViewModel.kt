package com.example.khushibaby.ui.screens.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.khushibaby.data.model.Patient
import com.example.khushibaby.data.model.Response
import com.example.khushibaby.data.repository.PatientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val patientRepository: PatientRepository) :
    ViewModel() {
    val resultState = MutableLiveData<Response<List<Patient?>>>()


    fun getPatients() {
        resultState.postValue(Response.Loading)
        viewModelScope.launch {
            try {
                val patients = patientRepository.getPatients()
                resultState.postValue(Response.Success(patients))
            } catch (e: Exception) {
                resultState.postValue(Response.Error(e, null))
            }
        }


    }
}
