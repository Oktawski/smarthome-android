package com.example.smarthome.user.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.smarthome.R
import com.example.smarthome.databinding.RegisterFragmentBinding
import com.example.smarthome.user.UserViewModel
import com.example.smarthome.user.models.User
import com.example.smarthome.utilities.LiveDataObservers
import com.example.smarthome.utilities.OnClickListeners
import com.example.smarthome.utilities.Resource

class RegistrationFragment:
    Fragment(R.layout.register_fragment),
    LiveDataObservers,
    OnClickListeners{

    private var _binding: RegisterFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UserViewModel by viewModels()

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
        setToolbarTitle()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = RegisterFragmentBinding.inflate(inflater, container, false)
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

    override fun initLiveDataObservers(){
        viewModel.status.observe(viewLifecycleOwner){
            when(it.status){
                Resource.Status.LOADING -> {
                    binding.register.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                }
                Resource.Status.SUCCESS -> {
                    binding.register.visibility= View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    toast(it.message)
                }
                Resource.Status.ERROR -> {
                    binding.register.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    toast(it.message)
                }
            }
        }
    }

    override fun initOnClickListeners(){
        binding.register.setOnClickListener{
            val username = binding.username.text.toString()
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()

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