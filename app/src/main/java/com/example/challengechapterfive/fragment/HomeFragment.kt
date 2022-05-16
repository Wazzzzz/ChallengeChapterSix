package com.example.challengechapterfive.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challengechapterfive.MainActivity
import com.example.challengechapterfive.mvvm.MainViewModel
import com.example.challengechapterfive.adapter.MainAdapter
import com.example.challengechapterfive.databinding.FragmentHomeBinding
import com.example.challengechapterfive.mvvm.UserViewModel
import com.example.challengechapterfive.room.NetplixDatabase
import com.example.challengechapterfive.room.UserAccount
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class HomeFragment : Fragment() {

    lateinit var userViewModel: UserViewModel
    private val viewModel: MainViewModel by viewModels()
    private var bindingPage: FragmentHomeBinding? = null
    private val binding get() = bindingPage!!
    var mDb: NetplixDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bindingPage = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences = requireContext()
            .getSharedPreferences(MainActivity.SHARED_PREFERENCES, Context.MODE_PRIVATE)
        userViewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]
        fetchAllData()
        binding.tvWelcome.text = "Welcome ${sharedPreferences?.getString("email", null)}"
        getUser()
    }

    private fun fetchAllData() {

        viewModel.movie.observe(viewLifecycleOwner){
            val adapter = MainAdapter(it)
            val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            binding.recyclerView.layoutManager = layoutManager
            binding.recyclerView.adapter = adapter
        }

        viewModel.code.observe(viewLifecycleOwner){
            if (it == 200){
                binding.progressBar.visibility = View.GONE
            }
        }



//        MoviesApi.instance.allMovie().enqueue(object : retrofit2.Callback<GetAllMovieResponse> {
//            override fun onResponse(
//                call: Call<GetAllMovieResponse>,
//                response: Response<GetAllMovieResponse>
//            ) {
//                val body = response.body()
//                val code = response.code()
//                if (code == 200) {
//                    showList(body)
//                    binding.progressBar.visibility = View.GONE
//                } else {
//                    binding.progressBar.visibility = View.GONE
//                }
//            }
//
//            override fun onFailure(call: Call<GetAllMovieResponse>, t: Throwable) {
//                binding.progressBar.visibility = View.GONE
//            }
//        })
    }

    private fun getUser() {
        val sharedPreferences = requireContext().getSharedPreferences(MainActivity.SHARED_PREFERENCES, Context.MODE_PRIVATE)
        mDb = NetplixDatabase.getInstance(requireContext())
        val email = sharedPreferences.getString("email", null).toString()
        val password = sharedPreferences.getString("password", null).toString()
        lifecycleScope.launch(Dispatchers.IO) {
            val data = mDb?.userAccountDao()?.login(email, password)
            runBlocking (Dispatchers.Main){
                if (
                    data != null
                ){
                    val user = UserAccount(data.id, data.name, data.email, data.password, data.fullName, data.birthDate, data.address)
                    userViewModel.getUserAccount(user)

                    binding.icProfile.setOnClickListener {
                        val direct = HomeFragmentDirections.actionHomeFragmentToProfileFragment(data)
                        findNavController().navigate(direct)
                    }
                }
            }
        }

    }

//    private fun showList(data: GetAllMovieResponse?) {
//
//        val adapter = MainAdapter(object : MainAdapter.OnClickListener {
//            override fun onCLickItem(data: Result) {
//            }
//        })
//        adapter.submitData(data)
//        binding.recyclerView.adapter = adapter
//}

    override fun onDestroy() {
        super.onDestroy()
        bindingPage = null
    }

}
