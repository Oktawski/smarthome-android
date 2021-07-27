package com.example.smarthome.ui.user

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.example.smarthome.R
import com.example.smarthome.databinding.LoginFragmentBinding
import com.example.smarthome.viewmodel.UserViewModel
import com.example.smarthome.data.model.LoginRequest
import com.example.smarthome.ui.main.DevicesPagerActivity
import com.example.smarthome.utilities.LiveDataObservers
import com.example.smarthome.utilities.OnClickListeners
import com.example.smarthome.utilities.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment:
    Fragment(R.layout.login_fragment),
    LiveDataObservers,
    OnClickListeners {

    private var _binding: LoginFragmentBinding? = null
    private val binding get() = _binding!!

    private val userViewModel: UserViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setToolbarTitle()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LoginFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        setToolbarTitle()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLiveDataObservers()
        initOnClickListeners()
    }

    private fun setToolbarTitle(){
        (activity as LoginActivity).supportActionBar?.title = "Login"
    }

    override fun initLiveDataObservers(){
        userViewModel.isSignedIn.observe(viewLifecycleOwner){
            startActivity(Intent(requireActivity(), DevicesPagerActivity::class.java))
        }

        userViewModel.status.observe(viewLifecycleOwner){
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

    override fun initOnClickListeners(){
        binding.login.setOnClickListener{
            if(!showErrorIfTextFieldEmpty(binding.username, binding.password)){
                val username = binding.username.text.toString()
                val password = binding.password.text.toString()

                val loginBody = LoginRequest(username, password)

                userViewModel.signin(loginBody)
            }
        }

        binding.register.setOnClickListener{
            val bundle = Bundle()

            val username = binding.username.text.toString()
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
        binding.login.visibility = View.GONE
        binding.register.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun showButtons(){
        binding.login.visibility = View.VISIBLE
        binding.register.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
    }

    private fun showErrorIfTextFieldEmpty(vararg text: EditText): Boolean{
        var isEmpty = false
        for(t in text){
            if(t.text.isNullOrEmpty()){
                t.error = "Cannot be empty"
                isEmpty = true
            }
        }
        return isEmpty
    }
}
