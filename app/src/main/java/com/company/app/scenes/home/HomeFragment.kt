package com.company.app.scenes.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.company.app.R
import com.company.app.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    // TODO: - Populate vm with args
    private val args: HomeFragmentArgs by navArgs()

//    private val vm by viewModel<HomeViewModel>()
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
//        binding.vm = vm
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.homeWelcomeMessage.text = getString(R.string.home_welcome_message, args.user.id)
    }
}