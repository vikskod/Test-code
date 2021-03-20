package com.example.testcode.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testcode.data.model.ParentResponse
import com.example.testcode.data.model.StudentResponse
import com.example.testcode.data.network.Resource
import com.example.testcode.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {

    private val _loginParentResponse: MutableLiveData<Resource<ParentResponse>> = MutableLiveData()
    val loginParentResponse: LiveData<Resource<ParentResponse>>
        get() = _loginParentResponse

    private val _loginStudentResponse: MutableLiveData<Resource<StudentResponse>> =
        MutableLiveData()
    val loginStudentResponse: LiveData<Resource<StudentResponse>>
        get() = _loginStudentResponse

    fun loginParent(
        username: String,
        password: String
    ) = viewModelScope.launch {
        _loginParentResponse.value = Resource.Loading
        _loginParentResponse.value = repository.loginParent(username, password)
    }

    fun loginParentAsStudent(
        token: String,
        studentId: String
    ) = viewModelScope.launch {
        _loginStudentResponse.value = Resource.Loading
        _loginStudentResponse.value = repository.loginParentAsStudent(token, studentId)
    }

    fun loginStudent(
        username: String,
        password: String
    ) = viewModelScope.launch {
        _loginStudentResponse.value = Resource.Loading
        _loginStudentResponse.value = repository.loginStudent(username, password)
    }

    suspend fun saveAuthToken(token: String) {
        repository.saveAuthToken(token)
    }

}