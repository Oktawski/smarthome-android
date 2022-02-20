package com.example.smarthome.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.smarthome.R
import com.example.smarthome.data.model.User
import com.example.smarthome.databinding.RegisterFragmentBinding
import com.example.smarthome.utilities.LiveDataObservers
import com.example.smarthome.utilities.OnClickListeners
import com.example.smarthome.utilities.Resource
import com.example.smarthome.viewmodel.UserViewModel

class RegistrationFragment:
    Fragment(R.layout.register_fragment),
    LiveDataObservers,
    OnClickListeners
{
    private var _binding: RegisterFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: UserViewModel

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
        viewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = RegisterFragmentBinding.inflate(inflater, container, false)
        binding.username.setText((requireArguments()["username"] ?: "").toString())
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

    override fun initLiveDataObservers() {
        viewModel.status.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> handleLoadingStatus()
                Resource.Status.SUCCESS -> handleSuccessStatus(it.message)
                Resource.Status.ERROR -> handleErrorStatus(it.message)
                else -> toast("Else? LOL idk")
            }
        }
    }

    override fun initOnClickListeners() {
        binding.register.setOnClickListener {
            val username = binding.username.text.toString()
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()

            val user = User(username, email, password)

            viewModel.signup(user)
        }
    }

    private fun setToolbarTitle() {
        (activity as LoginActivity).supportActionBar?.title = "Register"
    }

    private fun toast(str: String?) {
        if (str != null && str.isNotEmpty())
            Toast.makeText(requireActivity(), str, Toast.LENGTH_SHORT).show()
    }

    private fun handleLoadingStatus() {
        binding.register.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun handleSuccessStatus(message: String?) {
        binding.register.visibility= View.VISIBLE
        binding.progressBar.visibility = View.GONE
        NavHostFragment.findNavController(this)
            .navigate(R.id.action_viewLoginFragment)
        toast(message)
    }

    private fun handleErrorStatus(message: String?) {
        binding.register.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
        toast(message)
    }
}
