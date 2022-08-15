package com.company.app.scenes.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.company.app.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private val args: HomeFragmentArgs by navArgs()
    private var binding: FragmentHomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)
            .also { binding = it }
        binding.args = args
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    // Prevent memory retention of previously inflated binding
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
