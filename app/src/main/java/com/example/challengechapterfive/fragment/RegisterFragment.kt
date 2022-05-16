package com.example.challengechapterfive.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.challengechapterfive.R
import com.example.challengechapterfive.databinding.FragmentRegisterBinding
import com.example.challengechapterfive.room.NetplixDatabase
import com.example.challengechapterfive.room.UserAccount
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private var mDb: NetplixDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mDb = NetplixDatabase.getInstance(requireContext())

        binding.signupButton.setOnClickListener {
            val name = binding.etUsername.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val confirmPassword = binding.etConfirmPassword.text.toString()

            val regist = UserAccount(null,name,email,password )
            when {
                name.isNullOrEmpty() -> {
                    binding.materialUsername.error = "Kolom username harus diisi"
                }
                email.isNullOrEmpty() -> {
                    binding.materialEmail.error = "Kolom email harus diisi"
                }
                password.isNullOrEmpty() -> {
                    binding.materialPassword.error = "Kolom password harus diisi"
                }
                confirmPassword.isNullOrEmpty() -> {
                    binding.materialConfirmPassword.error = "Kolom konfirmasi password harus diisi"
                }
                password.lowercase() != confirmPassword.lowercase() -> {
                    binding.materialConfirmPassword.error = "Password dan konfirmasi password tidak sama"
                    binding.etConfirmPassword.setText("")
                }else-> {
                lifecycleScope.launch(Dispatchers.IO) {
                    val result = mDb?.userAccountDao()?.insertUserAccount(regist)
                    activity?.runOnUiThread {
                        if (result != 0.toLong()){
                            Toast.makeText(context, "Pendaftaran Berhasil", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(context, "Pendaftaran Gagal", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                val direct = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
                findNavController().navigate(direct)
            }
            }
        }

        binding.signinText.setOnClickListener{
            val direct = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
            findNavController().navigate(direct)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null}

}