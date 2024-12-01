package com.eltex.lab14.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.eltex.lab14.R
import com.eltex.lab14.databinding.FragmentBottomNavigationBinding

class BottomNavigationFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding = FragmentBottomNavigationBinding.inflate(inflater, container, false)

        val navController =
            requireNotNull(childFragmentManager.findFragmentById(R.id.container)).findNavController()


        val postClickListener = View.OnClickListener {
           findNavController().navigate(R.id.action_bottomNavigationFragment_to_newPostFragment)
        }
        val eventClickListener = View.OnClickListener {
            //navController.navigate(R.id.action_bottomNavigationFragment_to_newPostFragment)
        }

        binding.bottomNavigation.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.postFragment -> {
                    binding.newEvent.setOnClickListener(postClickListener)
                    binding.newEvent.animate()
                        .scaleX(1F)
                        .scaleY(1F)
                }

                R.id.eventFragment -> {
                   //binding.newEvent
                }

                R.id.userFragment -> {
                   // binding.newUser
                }
            }
        }

        return binding.root
    }
}