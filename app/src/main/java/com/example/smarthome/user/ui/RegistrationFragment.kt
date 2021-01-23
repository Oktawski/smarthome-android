package com.example.smarthome.user.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.smarthome.R
import com.example.smarthome.user.models.User
import com.example.smarthome.user.viewModels.UserViewModel
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class RegistrationFragment: Fragment() {

    private var model: UserViewModel? = null

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

        model = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)

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

        val etUsername = view.findViewById<EditText>(R.id.registration_et_username)
        val etEmail = view.findViewById<EditText>(R.id.registration_et_email)
        val etPassword = view.findViewById<EditText>(R.id.registration_et_password)
        val eFabRegistration = view.findViewById<ExtendedFloatingActionButton>(R.id.registration_fab)

        // TODO catch bundle

        model!!.getSignupMsg().observe(viewLifecycleOwner, Observer { str -> run{
            if(str.isNotEmpty()) Toast.makeText(requireActivity(), str, Toast.LENGTH_SHORT).show()
        } })

        eFabRegistration.setOnClickListener{
            val username = etUsername.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            val user = User(username, email, password)

            model!!.signup(user)
        }
    }

    private fun setToolbarTitle(){
        (activity as LoginActivity).supportActionBar?.title = "Register"
    }
}