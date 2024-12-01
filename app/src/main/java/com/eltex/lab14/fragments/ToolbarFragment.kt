package com.eltex.lab14.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.eltex.lab14.R
import com.eltex.lab14.databinding.FragmentToolbarBinding
import com.eltex.lab14.presentation.viewmodel.ToolbarViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlin.math.truncate


class ToolbarFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding = FragmentToolbarBinding.inflate(inflater, container, false)

        val navController =
            requireNotNull(childFragmentManager.findFragmentById(R.id.container)).findNavController()

        binding.toolbar.setupWithNavController(navController)

        val toolbarViewModel by activityViewModels<ToolbarViewModel>()

        val newPostItem = binding.toolbar.menu.findItem(R.id.save_post)

        toolbarViewModel.saveVisible.onEach {
            newPostItem.isVisible = it
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        newPostItem.setOnMenuItemClickListener {
            toolbarViewModel.onSaveClicked(true)
            true
        }

        return binding.root
    }

}