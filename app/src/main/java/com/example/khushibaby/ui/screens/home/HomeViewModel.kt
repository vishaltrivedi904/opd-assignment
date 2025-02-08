package com.example.khushibaby.ui.screens.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.khushibaby.data.model.Patient
import com.example.khushibaby.data.model.Response
import com.example.khushibaby.data.model.Visit
import com.example.khushibaby.data.repository.PatientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val patientRepository: PatientRepository) : ViewModel() {

    private val _resultState = MutableLiveData<Response<List<Patient?>>>()
    val resultState: LiveData<Response<List<Patient?>>> get() = _resultState


    fun getPatients() {
        _resultState.value= Response.Loading
        viewModelScope.launch {
            try {
                val patients = patientRepository.getPatients()
                _resultState.value= Response.Success(patients)
            } catch (e: Exception) {
                _resultState.value= Response.Error(e, null)
            }
        }


    }
}
