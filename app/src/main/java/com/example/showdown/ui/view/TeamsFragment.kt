package com.example.showdown.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.showdown.databinding.FragmentTeamsBinding

class TeamsFragment: Fragment() {
    private var _binding: FragmentTeamsBinding? = null
    private val binding: FragmentTeamsBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTeamsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            buildBtn.setOnClickListener {
                val direction = TeamsFragmentDirections.teamFragmentToTeamMembersFragmentAction()
                findNavController().navigate(direction)
            }
            buildBackBtn.setOnClickListener {
                val direction = TeamsFragmentDirections.teamFragmentToMainFragmentAction()
                findNavController().navigate(direction)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}