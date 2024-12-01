package com.eltex.lab14.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.eltex.lab14.Constants
import com.eltex.lab14.R
import com.eltex.lab14.data.Event
import com.eltex.lab14.databinding.FragmentPostBinding
import com.eltex.lab14.db.AppDb
import com.eltex.lab14.presentation.activity.NewEventActivity
import com.eltex.lab14.presentation.adapters.EventAdapter
import com.eltex.lab14.presentation.adapters.OffsetDecoration
import com.eltex.lab14.presentation.viewmodel.EventViewModel
import com.eltex.lab14.repository.SqliteEventsRepository
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
                        SqliteEventsRepository(
                            AppDb.getInstance(requireContext().applicationContext).eventDao
                        )
                    )
                }
            }
        }

        val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val content = it.data?.getStringExtra(Intent.EXTRA_TEXT)
            val id = it.data?.getLongExtra(Intent.EXTRA_INDEX, -1)
            if (id == Constants.ID_NON_EXISTENT_EVENT || id == null) {
                content?.let { viewModel.addEvent(content) }
            } else {
                content?.let { viewModel.updateEvent(id, content) }
            }
        }

        fun launchNewEventActivity(
            id: Long = Constants.ID_NON_EXISTENT_EVENT, newContent: String = ""
        ) {
            val intentNewEventActivity =
                Intent(requireContext(), NewEventActivity::class.java).putExtra(
                    Intent.EXTRA_INDEX, id
                ).putExtra(Intent.EXTRA_TEXT, newContent)
            launcher.launch(intentNewEventActivity)
        }


        val adapter = EventAdapter(object : EventAdapter.EventListener {
            override fun likeClickListener(event: Event) {
                viewModel.likeById(event.id)
            }

            override fun participateClickListener(event: Event) {
                viewModel.participateById(event.id)
            }

            override fun shareClickListener(event: Event) {
                share(event.content)
            }

            override fun menuClickListener() {}

            override fun onDeleteClickListener(event: Event) {
                viewModel.deleteById(event.id)
            }

            override fun onUpdateClickListener(event: Event) {
                launchNewEventActivity(event.id, event.content)
            }

        })



        binding.recyclerView.addItemDecoration(OffsetDecoration(resources.getDimensionPixelOffset(R.dimen.list_offset)))
        binding.recyclerView.adapter = adapter

        viewModel.uiState.flowWithLifecycle(viewLifecycleOwner.lifecycle).onEach {
            adapter.submitEventList(it.events)
        }.launchIn(viewLifecycleOwner.lifecycleScope)


        return binding.root
    }

    fun share(text: String) {
        val intent = Intent.createChooser(
            Intent(Intent.ACTION_SEND).putExtra(Intent.EXTRA_TEXT, text).setType("text/plain"), null
        )
        runCatching {
            startActivity(intent)
        }.onFailure {
            Toast.makeText(requireContext(), getString(R.string.app_not_found), Toast.LENGTH_SHORT)
                .show()
        }
    }
}