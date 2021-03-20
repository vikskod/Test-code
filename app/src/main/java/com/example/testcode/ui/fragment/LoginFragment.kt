package com.example.testcode.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.example.testcode.R
import com.example.testcode.data.UserPreferences
import com.example.testcode.data.network.Resource
import com.example.testcode.databinding.FragmentLoginBinding
import com.example.testcode.ui.viewmodel.LoginViewModel
import com.example.testcode.util.handleApiError
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        binding.btnLogin.setOnClickListener(this)

        // Observe the login request from parent
        viewModel.loginParentResponse.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        viewModel.saveAuthToken(it.value.authToken)
                    }
                    // TODO navigate to main page after parent login
                }
                is Resource.Failure -> handleApiError(it) {
                    // TODO handle API error
                }
            }
        })
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.btnLogin -> {
                viewModel.loginParent(
                    binding.etUsername.text.toString(),
                    binding.etPassword.text.toString()
                )
            }
        }
    }

    // Put this code on suitable UI where parents wants to login as a student.
    fun onParentLoginAsStudent(studentID: String) {
        val userPreferences = context?.let { UserPreferences(it) }
        userPreferences?.authToken?.asLiveData()?.observe(this, Observer {
            if (it != null) {
                viewModel.loginParentAsStudent(it, studentID)
            }
        })

        // observe the login request from parent as a student
        viewModel.loginStudentResponse.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    // TODO navigate to main page after parent login
                }
                is Resource.Failure -> handleApiError(it) {
                    // TODO handle API error
                }
            }
        })
    }

    // TODO put this code on suitable UI
    // Student wants to login
    fun onStudentLogin(studentID: String) {
        viewModel.loginStudent(
            binding.etUsername.text.toString(),
            binding.etPassword.text.toString()
        )

        // observe the login request from parent as a student
        viewModel.loginStudentResponse.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    // TODO navigate to main page after parent login
                }
                is Resource.Failure -> handleApiError(it) {
                    // TODO handle API error
                }
            }
        })
    }

    // TODO Make a suitable UI in which user can do logout
    fun logOutUser(){
        // Call the logout api with the help of ViewModel class
    }
}