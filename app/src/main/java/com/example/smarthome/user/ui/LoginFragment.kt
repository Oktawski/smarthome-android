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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.smarthome.DevicesPagerActivity
import com.example.smarthome.R
import com.example.smarthome.user.models.LoginRequest
import com.example.smarthome.user.viewModels.UserViewModel
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import java.net.PasswordAuthentication

class LoginFragment: Fragment() {

//    private var viewModel: UserViewModel? = null
    private lateinit var viewModel: UserViewModel
    lateinit var etUsername: EditText
    lateinit var etPassword: EditText
    lateinit var eFabLogin: ExtendedFloatingActionButton
    lateinit var eFabRegister: ExtendedFloatingActionButton
    lateinit var pb: ProgressBar

    companion object{
        @JvmStatic
        fun newInstance() = LoginFragment()
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
        return inflater.inflate(R.layout.login_login_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etUsername = view.findViewById(R.id.login_et_username)
        etPassword = view.findViewById(R.id.login_et_password)
        eFabLogin = view.findViewById(R.id.login_fab_login)
        eFabRegister = view.findViewById(R.id.login_fab_register)
        pb = view.findViewById(R.id.login_pb)

        initViewModel()

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

    private fun setToolbarTitle(){
        (activity as LoginActivity).supportActionBar?.title = "Login"
    }

    private fun initViewModel(){
        viewModel?.getIsSignedIn()?.observe(viewLifecycleOwner, Observer {bool -> run{
            startActivity(Intent(requireActivity(), DevicesPagerActivity::class.java))
        }})

        viewModel?.getLoginMsg()?.observe(viewLifecycleOwner, Observer{str -> run{
            toastResponseMsg(str)
        }})

        viewModel?.getSignupMsg()?.observe(viewLifecycleOwner, Observer{str -> run{
            toastResponseMsg(str)
        }})

        viewModel?.showProgressBar()?.observe(viewLifecycleOwner, Observer {bool -> run{
            if(bool){
                eFabLogin.hide()
                eFabRegister.hide()
                pb.visibility = View.VISIBLE
            }
            else{
                eFabLogin.show()
                eFabRegister.show()
                pb.visibility = View.GONE
            }
        }})
    }

    private fun toastResponseMsg(str: String){
        if(str.isNotEmpty()){
            Toast.makeText(requireActivity(), str, Toast.LENGTH_SHORT).show()
        }
    }
}