package com.example.testcode.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testcode.data.model.ParentResponse
import com.example.testcode.data.model.StudentResponse
import com.example.testcode.repository.DefaultMainRepository
import com.example.testcode.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: DefaultMainRepository
) : ViewModel() {

    private val _loginParentResponse: MutableLiveData<Resource<ParentResponse>> = MutableLiveData()
    val loginParentResponse: LiveData<Resource<ParentResponse>>
        get() = _loginParentResponse

    fun loginParent(
        username: String,
        password: String
    ) = viewModelScope.launch(Dispatchers.IO) {
        _loginParentResponse.postValue(Resource.Loading)
        _loginParentResponse.postValue(repository.getParentLogin(username, password))
    }

    suspend fun saveAuthToken(token: String) {
        repository.saveAuthToken(token)
    }

}