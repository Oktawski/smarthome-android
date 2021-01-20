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
import com.example.smarthome.MainActivity
import com.example.smarthome.R
import com.example.smarthome.user.models.LoginBody
import com.example.smarthome.user.viewModels.UserViewModel
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class LoginFragment: Fragment() {

    private var model: UserViewModel? = null;

    companion object{
        @JvmStatic
        fun newInstance() = LoginFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        model = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)

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

        val etUsername = view.findViewById<EditText>(R.id.login_et_username)
        val etPassword = view.findViewById<EditText>(R.id.login_et_password)
        val eFabLogin = view.findViewById<ExtendedFloatingActionButton>(R.id.login_fab_login)
        val eFabRegister = view.findViewById<ExtendedFloatingActionButton>(R.id.login_fab_register)
        val pb = view.findViewById<ProgressBar>(R.id.login_pb)

        model!!.getLoginMsg().observe(viewLifecycleOwner, Observer { str -> run{
            if(str.isNotEmpty()) Toast.makeText(requireActivity(), str, Toast.LENGTH_SHORT).show()
        } })

        model!!.getSignupMsg().observe(viewLifecycleOwner, Observer{ str -> run{
            if(str.isNotEmpty()) Toast.makeText(requireActivity(), str, Toast.LENGTH_SHORT).show()
        } })

        model!!.showProgressBar().observe(viewLifecycleOwner, Observer { bool -> run{
            if(bool){
                eFabLogin.visibility = View.INVISIBLE
                pb.visibility = View.VISIBLE
            }
            else{
                eFabLogin.visibility = View.VISIBLE
                pb.visibility = View.GONE
            }
        } })

        model!!.getIsSignedIn().observe(viewLifecycleOwner, Observer { bool -> run{
            if(bool) {
                startActivity(Intent(requireActivity(), MainActivity::class.java))
            }
        } })

        eFabLogin.setOnClickListener{
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            val loginBody = LoginBody(username, password)

            model!!.signin(loginBody)
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
        (activity as LoginActivity).supportActionBar?.title = "Login";
    }
}