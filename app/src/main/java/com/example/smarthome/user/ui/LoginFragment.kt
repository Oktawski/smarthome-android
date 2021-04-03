package com.example.smarthome.user.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.NavHostFragment
import com.example.smarthome.DevicesPagerActivity
import com.example.smarthome.R
import com.example.smarthome.user.models.LoginRequest
import com.example.smarthome.user.viewModels.UserViewModel
import com.example.smarthome.utilities.Resource
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class LoginFragment: Fragment() {

    private lateinit var viewModel: UserViewModel
    lateinit var etUsername: EditText
    lateinit var etPassword: EditText
    lateinit var eFabLogin: ExtendedFloatingActionButton
    lateinit var eFabRegister: ExtendedFloatingActionButton
    lateinit var pb: ProgressBar


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
        return inflater.inflate(R.layout.login_login_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etUsername = view.findViewById(R.id.login_et_username)
        etPassword = view.findViewById(R.id.login_et_password)
        eFabLogin = view.findViewById(R.id.login_fab_login)
        eFabRegister = view.findViewById(R.id.login_fab_register)
        pb = view.findViewById(R.id.login_pb)

        initViewModelObservables()
        initOnClickListeners()
    }

    private fun setToolbarTitle(){
        (activity as LoginActivity).supportActionBar?.title = "Login"
    }

    private fun initViewModelObservables(){
        viewModel.getIsSignedIn().observe(viewLifecycleOwner){
            startActivity(Intent(requireActivity(), DevicesPagerActivity::class.java))
        }

        viewModel.getStatus().observe(viewLifecycleOwner){
            when(it.status){
                Resource.Status.SUCCESS -> {
                    showButtons()
                    toast(it.message)
                }
                Resource.Status.LOADING -> hideButtons()

                Resource.Status.ERROR -> {
                    showButtons()
                    toast(it.message)
                }
            }
        }
    }

    private fun initOnClickListeners(){
        eFabLogin.setOnClickListener{
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            val loginBody = LoginRequest(username, password)

            viewModel!!.signin(loginBody)
        }

        eFabRegister.setOnClickListener{
            val bundle = Bundle()

            val username = etUsername.text.toString()
            if(username.isNotEmpty()){
                bundle.putString("username", username)
            }

            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_viewRegistrationFragment, bundle)
        }
    }

    private fun toast(str: String?){
        if(str != null && str.isNotEmpty()){
            Toast.makeText(requireActivity(), str, Toast.LENGTH_SHORT).show()
        }
    }

    private fun hideButtons(){
        eFabLogin.hide()
        eFabRegister.hide()
        pb.visibility = View.VISIBLE
    }

    private fun showButtons(){
        eFabLogin.show()
        eFabRegister.show()
        pb.visibility = View.GONE
    }
}