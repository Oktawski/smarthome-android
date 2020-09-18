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
import androidx.navigation.fragment.NavHostFragment;

import com.example.smarthome.R;
import com.example.smarthome.user.viewModels.UserViewModel;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class LoginFragment extends Fragment {

    private UserViewModel model;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = new ViewModelProvider(getActivity()).get(UserViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login_login_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final EditText etEmail = view.findViewById(R.id.login_et_email);
        final EditText etPassword = view.findViewById(R.id.login_et_password);
        final ExtendedFloatingActionButton eFabLogin = view.findViewById(R.id.login_fab_login);
        final ExtendedFloatingActionButton eFabRegister = view.findViewById(R.id.login_fab_register);

        eFabLogin.setOnClickListener(v -> {
            Toast.makeText(getActivity(), "login clicked", Toast.LENGTH_SHORT).show();

            //TODO implement signing in

        });

        eFabRegister.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            if(!etEmail.getText().equals("")){
                bundle.putString("email", etEmail.getText().toString());
            }
            if(!etPassword.getText().equals("")){
                bundle.putString("password", etPassword.getText().toString());
            }

            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_viewRegistrationFragment, bundle);
        });
    }
}
