package com.company.app.scenes.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.company.app.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {
    private val vm by viewModel<LoginViewModel>()
    private var binding: FragmentLoginBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentLoginBinding.inflate(inflater, container, false)
            .also { binding = it }
        binding.vm = vm
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        vm.loginSuccess.observe(viewLifecycleOwner) {
            val directions = LoginFragmentDirections.fromLoginToHome(it)
            findNavController().navigate(directions)
        }
        vm.loginFailure.observe(viewLifecycleOwner) {
            val root = binding?.root ?: return@observe
            Snackbar.make(root, it, Snackbar.LENGTH_SHORT).show()
        }
    }

    // Prevent memory retention of previously inflated binding
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
