package com.example.challengechapterfive.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.challengechapterfive.MainActivity
import com.example.challengechapterfive.R
import com.example.challengechapterfive.databinding.FragmentHomeBinding
import com.example.challengechapterfive.databinding.FragmentProfileBinding
import com.example.challengechapterfive.mvvm.UserViewModel
import com.example.challengechapterfive.room.NetplixDatabase
import com.example.challengechapterfive.room.UserAccount
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ProfileFragment : Fragment() {

    private var bindingPage: FragmentProfileBinding? = null
    private val binding get() = bindingPage!!
    var mDb: NetplixDatabase? = null
    lateinit var userViewModel: UserViewModel
    private val args: ProfileFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bindingPage = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreference = requireContext().getSharedPreferences(MainActivity.SHARED_PREFERENCES, Context.MODE_PRIVATE)
        mDb = NetplixDatabase.getInstance(requireContext())
        userViewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]

        binding.etUsername.setText(args.user.name)
        binding.etFullname.setText(args.user.fullName)
        binding.etBirthDate.setText(args.user.birthDate)
        binding.etAddress.setText(args.user.address)

        binding.btnUpdate.setOnClickListener {
            val user = UserAccount(args.user.id, binding.etUsername.text.toString(), args.user.email, args.user.password, binding.etFullname.text.toString(), binding.etBirthDate.text.toString(), binding.etAddress.text.toString())
            lifecycleScope.launch (Dispatchers.IO){
                val result = mDb?.userAccountDao()?.updateAccount(user)
                runBlocking (Dispatchers.Main) {
                    if (result != null) {
                        userViewModel.getUserAccount(user)
                        val direct = ProfileFragmentDirections.actionProfileFragmentToHomeFragment()
                        findNavController().navigate(direct)
                    }
                }
            }
        }
        binding.btnLogout.setOnClickListener {
            val editor = sharedPreference.edit()
            editor.clear()
            editor.apply()
            val direct = ProfileFragmentDirections.actionProfileFragmentToLoginFragment()
            findNavController().navigate(direct)
        }
    }

}