package com.eltex.lab14.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.eltex.lab14.R
import com.eltex.lab14.databinding.ActivityMainBinding
import com.eltex.lab14.presentation.adapters.EventAdapter
import com.eltex.lab14.presentation.adapters.OffsetDecoration
import com.eltex.lab14.presentation.viewmodel.EventViewModel
import com.eltex.lab14.repository.InMemoryEventRepository
import com.eltex.lab14.utils.toast
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding ?: throw IllegalStateException("Binding for ActivityMain must not be null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val viewModel by viewModels<EventViewModel> {
            viewModelFactory {
                addInitializer(EventViewModel::class) {
                    EventViewModel(InMemoryEventRepository())
                }
            }
        }


        val adapter = EventAdapter(likeClickListener = { viewModel.likeById(it.id) },
            participateClickListener = { viewModel.participateById(it.id) },
            ShareClickListener = { toast(R.string.toastNotImplemented, false) },
            MenuClickListener = { toast(R.string.toastNotImplemented, true) })


        binding.root.addItemDecoration(OffsetDecoration(resources.getDimensionPixelOffset(R.dimen.list_offset)))
        binding.root.adapter = adapter


        viewModel.uiState.onEach {
            adapter.submitList(it.events)
        }.launchIn(lifecycleScope)


    }
}