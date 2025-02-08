package com.example.khushibaby.ui.screens.visit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.khushibaby.data.model.Response
import com.example.khushibaby.data.model.Visit
import com.example.khushibaby.data.repository.VisitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class VisitViewModel @Inject constructor(private val visitRepository: VisitRepository) :
    ViewModel() {
    private val _visit = MutableLiveData<Response<Visit>>()
    val visit: LiveData<Response<Visit>> get() = _visit


    fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
        return dateFormat.format(Date())
    }




    fun saveVisitDetails(
        patientId: Long,
        symptoms: String,
        diagnosis: String,
        medicines: String,
        dosage: String,
        frequency: String,
        duration: String
    ) {
        _visit.value = Response.Loading
        viewModelScope.launch {

            try {
                val visitDetail = Visit(
                    patientId = patientId,
                    visitDate = SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH).format(Date()),
                    symptoms = symptoms,
                    diagnosis = diagnosis,
                    medicines = medicines,
                    dosage = dosage,
                    frequency = frequency,
                    duration = duration
                )
                visitRepository.insertVisit(visitDetail)
                _visit.value = Response.Success(visitDetail) // Updating UI state
            } catch (e: Exception) {
                _visit.value = Response.Error(e, null)
            }
        }
    }

}
