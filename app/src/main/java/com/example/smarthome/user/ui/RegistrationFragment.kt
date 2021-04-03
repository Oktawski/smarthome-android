package com.example.smarthome.user.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.example.smarthome.R
import com.example.smarthome.user.models.User
import com.example.smarthome.user.viewModels.UserViewModel
import com.example.smarthome.utilities.Resource
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class RegistrationFragment: Fragment() {

    private lateinit var viewModel: UserViewModel
    private lateinit var eFabRegister: ExtendedFloatingActionButton
    private lateinit var etUsername: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText

    companion object{
        @JvmStatic
        fun newInstance(): RegistrationFragment {
            val args = Bundle()
            val fragment = RegistrationFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)

        setToolbarTitle()
    }

    override fun onResume() {
        super.onResume()
        setToolbarTitle()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.login_registration_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etUsername = view.findViewById(R.id.registration_et_username)
        etEmail = view.findViewById(R.id.registration_et_email)
        etPassword = view.findViewById(R.id.registration_et_password)
        eFabRegister = view.findViewById(R.id.registration_fab)

        initViewModelObservables()
        initOnClickListeners()
    }

    private fun initViewModelObservables(){
        viewModel.getStatus().observe(viewLifecycleOwner){
            when(it.status){
                Resource.Status.LOADING -> {
                    eFabRegister.hide()
                }
                Resource.Status.SUCCESS -> {
                    eFabRegister.show()
                    toast(it.message)
                }
                Resource.Status.ERROR -> {
                    eFabRegister.show()
                    toast(it.message)
                }
            }
        }
    }

    private fun initOnClickListeners(){
        eFabRegister.setOnClickListener{
            val username = etUsername.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            val user = User(username, email, password)

            viewModel.signup(user)
        }
    }

    private fun setToolbarTitle(){
        (activity as LoginActivity).supportActionBar?.title = "Register"
    }

    private fun toast(str: String?){
        if(str != null && str.isNotEmpty()){
            Toast.makeText(requireActivity(), str, Toast.LENGTH_SHORT).show()
        }
    }
}