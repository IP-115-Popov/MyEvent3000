package com.eltex.lab14.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.findNavController
import com.eltex.lab14.R
import com.eltex.lab14.data.Event
import com.eltex.lab14.databinding.FragmentPostBinding
import com.eltex.lab14.presentation.adapters.EventAdapter
import com.eltex.lab14.presentation.adapters.OffsetDecoration
import com.eltex.lab14.presentation.viewmodel.EventViewModel
import com.eltex.lab14.repository.NetworkEventsRepository
import com.eltex.lab14.util.getErrorText
import com.eltex.lab14.utils.share
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class PostFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        val binding = FragmentPostBinding.inflate(inflater, container, false)

        val viewModel by viewModels<EventViewModel> {
            viewModelFactory {
                addInitializer(EventViewModel::class) {
                    EventViewModel(
                        NetworkEventsRepository()
                    )
                }
            }
        }


        val adapter = EventAdapter(object : EventAdapter.EventListener {
            override fun likeClickListener(event: Event) {
                viewModel.likeById(event.id)
            }

            override fun participateClickListener(event: Event) {
                viewModel.participateById(event.id)
            }

            override fun shareClickListener(event: Event) {
                requireContext().share(event.content)
            }

            override fun menuClickListener() {}

            override fun onDeleteClickListener(event: Event) {
                viewModel.deleteById(event.id)
            }

            override fun onUpdateClickListener(event: Event) {
                requireParentFragment().requireParentFragment().findNavController().navigate(
                    R.id.action_bottomNavigationFragment_to_newPostFragment, bundleOf(
                        NewPostFragment.POST_ID to event.id,
                        NewPostFragment.CONTENT to event.content,
                    )
                )
            }

        })
        binding.recyclerView.addItemDecoration(OffsetDecoration(resources.getDimensionPixelOffset(R.dimen.list_offset)))
        binding.recyclerView.adapter = adapter

        binding.bthRetry.setOnClickListener {
            viewModel.load()
        }

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.load()
        }

        viewModel.uiState.flowWithLifecycle(viewLifecycleOwner.lifecycle).onEach { state ->
            binding.errorGroup.isVisible = state.isEmptyError
            val errorText = state.status.throwableOrNull?.getErrorText(requireContext())
            binding.tvError.text = errorText

            binding.progress.isVisible = state.isEmptyLoading

            binding.swipeRefresh.isRefreshing = state.isRefreshing

            if (state.isRefreshError) {
                Toast.makeText(requireContext(), errorText, Toast.LENGTH_SHORT).show()

                viewModel.consumeError()
            }

            state.events?.let { adapter.submitEventList(it) }
        }.launchIn(viewLifecycleOwner.lifecycleScope)


        return binding.root
    }
}