package com.example.testcode.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.testcode.R
import com.example.testcode.databinding.FragmentLoginBinding
import com.example.testcode.ui.viewmodel.LoginViewModel
import com.example.testcode.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

        binding.btnLogin.setOnClickListener(this)

        // Observe the login request from parent
        viewModel.loginParentResponse.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> {
                    Log.i("LoginFragment", "LOADING...")
                }
                is Resource.Success -> {
                    Log.i("LoginFragment", "SUCCESS...")
                    lifecycleScope.launch {
                        viewModel.saveAuthToken(it.value.authToken)
                    }
                    // TODO navigate to main page after parent login
                }
                is Resource.Failure -> {
                    Log.i("LoginFragment", "Error NetworkError ${it.isNetworkError} Code ${it.errorCode} Body: ${it.errorBody}")
                }
                else -> {
                    Log.i("LoginFragment", "ELSE...")
                }
            }
        })
    }

    /*
    * This code will execute when user Clicks on Login Button
    * */
    override fun onClick(v: View?) {
        when (v) {
            binding.btnLogin -> {
                // Put validation before calling login
                viewModel.loginParent(
                    binding.etUsername.text.toString(),
                    binding.etPassword.text.toString()
                )
            }
        }
    }
}