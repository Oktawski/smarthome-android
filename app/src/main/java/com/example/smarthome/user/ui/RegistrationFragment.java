package com.example.smarthome.user.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.smarthome.R;
import com.example.smarthome.user.models.User;
import com.example.smarthome.user.viewModels.UserViewModel;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class RegistrationFragment extends Fragment {
    
    private UserViewModel model;

    public static RegistrationFragment newInstance() {

        Bundle args = new Bundle();
        
        RegistrationFragment fragment = new RegistrationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        model = new ViewModelProvider(getActivity()).get(UserViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login_registration_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final EditText etUsername = view.findViewById(R.id.registration_et_username);
        final EditText etEmail = view.findViewById(R.id.registration_et_email);
        final EditText etPassword = view.findViewById(R.id.registration_et_password);
        final ExtendedFloatingActionButton eFabRegistration = view.findViewById(R.id.registration_fab);

        try {
            String emailFromBundle = getArguments().getString("email");
            String passwordFromBundle = getArguments().getString("password");
            etEmail.setText(emailFromBundle);
            etPassword.setText(passwordFromBundle);
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        model.getSignupMsg().observe(getViewLifecycleOwner(), str -> {
            Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
        });

        eFabRegistration.setOnClickListener(v -> {
            String username = etUsername.getText().toString();
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();

            User user = new User(username, email, password);

            model.signup(user);
        });



    }
}
