package com.eltex.lab14.feature.newevent.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.findNavController
import com.eltex.lab14.R
import com.eltex.lab14.databinding.FragmentNewPostBinding
import com.eltex.lab14.feature.events.repository.NetworkEventsRepository
import com.eltex.lab14.feature.newevent.viewmodel.NewPostViewModel
import com.eltex.lab14.feature.toolbar.viewmodel.ToolbarViewModel
import com.eltex.lab14.util.getErrorText
import com.eltex.lab14.utils.toast
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class NewPostFragment : Fragment() {


    companion object {
        const val POST_ID = "POST_ID"
        const val CONTENT = "CONTENT"
        const val POST_CREATED_KEY = "POST_CREATED_KEY"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        val binding = FragmentNewPostBinding.inflate(layoutInflater, container, false)

        val toolbarViewModel by activityViewModels<ToolbarViewModel>()

        val postId = arguments?.getLong(POST_ID) ?: 0L
        val content = arguments?.getString(CONTENT) ?: ""

        binding.etvContent.setText(content)

        val newPostViewModel by viewModels<NewPostViewModel> {
            viewModelFactory {
                addInitializer(
                    NewPostViewModel::class
                ) {
                    NewPostViewModel(
                        NetworkEventsRepository(), postId
                    )
                }
            }
        }
        toolbarViewModel.saveClicked.filter { it }.onEach {
            val content = binding.etvContent.text?.toString().orEmpty()

            if (content.isNotBlank()) {
                newPostViewModel.save(content)
                // findNavController().navigateUp()
            } else {
                requireContext().toast(R.string.content_is_epmty, false)
            }
            toolbarViewModel.onSaveClicked(false)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        newPostViewModel.state.flowWithLifecycle(viewLifecycleOwner.lifecycle).onEach {
            if (it.event != null) {
                requireActivity().supportFragmentManager.setFragmentResult(
                    POST_CREATED_KEY, bundleOf()
                )
                findNavController().navigateUp()
            }
            it.status.throwableOrNull?.getErrorText((requireContext()))?.let { errorText ->
                Toast.makeText(requireContext(), errorText, Toast.LENGTH_SHORT).show()
                newPostViewModel.consumeError()
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewLifecycleOwner.lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                when (event) {
                    Lifecycle.Event.ON_START -> toolbarViewModel.setSaveVisible(true)

                    Lifecycle.Event.ON_STOP -> toolbarViewModel.setSaveVisible(false)
                    Lifecycle.Event.ON_DESTROY -> source.lifecycle.removeObserver(this)
                    else -> Unit
                }
            }
        })



        return binding.root
    }
}