package com.eltex.lab14.feature.events.fragment

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
import androidx.recyclerview.widget.RecyclerView
import com.eltex.lab14.R
import com.eltex.lab14.databinding.FragmentPostBinding
import com.eltex.lab14.feature.events.effecthandler.EventEffectHandler
import com.eltex.lab14.feature.events.reducer.EventReducer
import com.eltex.lab14.feature.events.repository.NetworkEventsRepository
import com.eltex.lab14.feature.events.ui.EventUiModel
import com.eltex.lab14.feature.events.ui.EventUiModelMapper
import com.eltex.lab14.feature.events.viewmodel.EventMessage
import com.eltex.lab14.feature.events.viewmodel.EventStore
import com.eltex.lab14.feature.events.viewmodel.EventUiState
import com.eltex.lab14.feature.events.viewmodel.EventViewModel
import com.eltex.lab14.feature.newevent.fragment.NewEventFragment
import com.eltex.lab14.presentation.adapters.EventAdapter
import com.eltex.lab14.presentation.adapters.OffsetDecoration
import com.eltex.lab14.util.getErrorText
import com.eltex.lab14.utils.share
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class EventFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        val binding = FragmentPostBinding.inflate(inflater, container, false)

        val viewModel by viewModels<EventViewModel> {
            viewModelFactory {
                addInitializer(EventViewModel::class) {
                    EventViewModel(
                        EventStore(
                            EventReducer(),
                            EventEffectHandler(
                                NetworkEventsRepository(),
                                EventUiModelMapper()
                            ),
                            setOf(EventMessage.Refresh),
                            EventUiState()
                        )
                    )
                }
            }
        }

        requireActivity().supportFragmentManager.setFragmentResultListener(
            NewEventFragment.POST_CREATED_KEY, viewLifecycleOwner
        ) { _, _ ->
            viewModel.accept(EventMessage.Refresh)
        }

        val adapter = EventAdapter(object : EventAdapter.EventListener {
            override fun likeClickListener(event: EventUiModel) {
                viewModel.accept(EventMessage.Like(event))
            }

            override fun participateClickListener(event: EventUiModel) {
                viewModel.accept(EventMessage.Participate(event))
            }

            override fun shareClickListener(event: EventUiModel) {
                requireContext().share(event.content)
            }

            override fun menuClickListener() {}

            override fun onDeleteClickListener(event: EventUiModel) {
                viewModel.accept(EventMessage.Delete(event))
            }

            override fun onUpdateClickListener(event: EventUiModel) {
                requireParentFragment().requireParentFragment().findNavController().navigate(
                    R.id.action_bottomNavigationFragment_to_newPostFragment, bundleOf(
                        NewEventFragment.POST_ID to event.id,
                        NewEventFragment.CONTENT to event.content,
                    )
                )
            }

        })
        binding.recyclerView.addItemDecoration(OffsetDecoration(resources.getDimensionPixelOffset(R.dimen.list_offset)))
        binding.recyclerView.adapter = adapter

        binding.bthRetry.setOnClickListener {
            viewModel.accept(EventMessage.Refresh)
        }

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.accept(EventMessage.Refresh)
        }

        binding.recyclerView.addOnChildAttachStateChangeListener(
            object : RecyclerView.OnChildAttachStateChangeListener {
                override fun onChildViewAttachedToWindow(view: View) {
                    val itemsCount = adapter.itemCount
                    val adapterPosition = binding.recyclerView.getChildAdapterPosition(view)

                    if (itemsCount - 1 == adapterPosition) {
                        viewModel.accept(EventMessage.LoadNextPage)
                    }
                }

                override fun onChildViewDetachedFromWindow(view: View) = Unit
            }
        )
        viewModel.uiState.flowWithLifecycle(viewLifecycleOwner.lifecycle).onEach { state ->
            binding.errorGroup.isVisible = state.isEmptyError
            val errorText = state.emptyError?.getErrorText(requireContext())
            binding.tvError.text = errorText

            binding.progress.isVisible = state.isEmptyLoading

            binding.swipeRefresh.isRefreshing = state.isRefreshing

            if (state.singleError != null) {
                val singleErrorText = state.singleError.getErrorText(requireContext())
                Toast.makeText(requireContext(), singleErrorText, Toast.LENGTH_SHORT).show()

                viewModel.accept(EventMessage.HandleError)
            }

            adapter.submitEventList(state.events)
        }.launchIn(viewLifecycleOwner.lifecycleScope)


        return binding.root
    }
}