package com.example.khushibaby.ui.screens.registration

import androidx.lifecycle.LiveData
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
class RegistrationViewModel @Inject constructor(private val patientRepository: PatientRepository) : ViewModel() {

    private val _patient = MutableLiveData<Response<Patient>>()
    val patient: LiveData<Response<Patient>> get() = _patient

    fun registerPatient(name: String, age: Int, gender: String, contactNumber: String?, location: String, healthId: String) {
        _patient.value = Response.Loading
        viewModelScope.launch {
            try {
                val patient = Patient(
                    name = name,
                    age = age,
                    gender = gender,
                    contactNumber = contactNumber,
                    location = location,
                    healthId = healthId
                )
                patientRepository.insertPatient(patient)
                _patient.value = Response.Success(patient)
            } catch (e: Exception) {
                _patient.value = Response.Error(e, null)
            }
        }

    }
}
