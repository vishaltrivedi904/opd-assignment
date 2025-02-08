package com.example.khushibaby.ui.screens.summery

import androidx.collection.objectFloatMap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.khushibaby.data.model.BaseResponse
import com.example.khushibaby.data.model.Patient
import com.example.khushibaby.data.model.Response
import com.example.khushibaby.data.model.Visit
import com.example.khushibaby.data.repository.PatientRepository
import com.example.khushibaby.data.repository.VisitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class SummeryViewModel @Inject constructor(private val patientRepository: PatientRepository, private val visitRepository: VisitRepository) : ViewModel() {

    private val _patient = MutableLiveData<Response<Patient?>>()
    val patient: LiveData<Response<Patient?>> get() = _patient

    private val _visit = MutableLiveData<Response<Visit?>>()
    val visit: LiveData<Response<Visit?>> get() = _visit

    private val _visitComplete = MutableLiveData<Response<BaseResponse>>()
    val visitComplete: LiveData<Response<BaseResponse>> get() = _visitComplete


    fun getPatient(patientId: Long) {
        _patient.value = Response.Loading
        viewModelScope.launch {
            try {
                val patients = patientRepository.getPatient(patientId)
                _patient.value = Response.Success(patients)
            } catch (e: Exception) {
                _patient.value = Response.Error(e, null)
            }
        }
    }

    fun getVisit(visitId: Long) {
        _patient.value = Response.Loading
        viewModelScope.launch {
            try {
                val visit = visitRepository.getVisitForPatient(visitId)
                _visit.value = Response.Success(visit)
            } catch (e: Exception) {
                _visit.value = Response.Error(e, null)
            }
        }
    }

    fun markVisitAsCompleted(visit: Visit) {
        _visitComplete.value = Response.Loading
        viewModelScope.launch {
            try {
                val response=visitRepository.markVisitAsCompleted(visit)
                if (response.isSuccess)
                {
                    _visitComplete.value = Response.Success(response)
                } else {
                    val response = retrofit2.Response.error<String>(400, ResponseBody.create(null, "Something went wrong"))
                    val httpException = HttpException(response)
                    _visitComplete.value = Response.Error(httpException, null)
                }


            } catch (e: Exception) {
                _visitComplete.value = Response.Error(e, null)
            }
        }
    }
}
