package com.eltex.lab14.presentation.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.eltex.lab14.Constants
import com.eltex.lab14.R
import com.eltex.lab14.data.Event
import com.eltex.lab14.databinding.ActivityMainBinding
import com.eltex.lab14.db.AppDb
import com.eltex.lab14.presentation.adapters.EventAdapter
import com.eltex.lab14.presentation.adapters.OffsetDecoration
import com.eltex.lab14.presentation.ui.EdgeToEdgeHelper
import com.eltex.lab14.presentation.viewmodel.EventViewModel
import com.eltex.lab14.repository.SqliteEventsRepository
import com.eltex.lab14.utils.share
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
                    EventViewModel(SqliteEventsRepository(
                        AppDb.getInstance(applicationContext).eventDao
                    ))
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
                Intent(this, NewEventActivity::class.java).putExtra(Intent.EXTRA_INDEX, id)
                    .putExtra(Intent.EXTRA_TEXT, newContent)
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



        binding.bthNewEvent.setOnClickListener {
            launchNewEventActivity()
        }



        viewModel.uiState.onEach {
            adapter.submitMyList(it.events)
        }.launchIn(lifecycleScope)


        if (intent.action == Intent.ACTION_SEND) { // Обязательно проверьте соответствует ли action ожидаемому
            val text = intent.getStringExtra(Intent.EXTRA_TEXT)
            intent.removeExtra(Intent.EXTRA_TEXT) // Удаляем, чтобы при повороте экрана снова не открывалась активити

            text?.let {
                launchNewEventActivity(Constants.ID_NON_EXISTENT_EVENT, text)
            }
        }

        applyInserts()
    }

    private fun applyInserts() {
        enableEdgeToEdge()
        EdgeToEdgeHelper.enableEdgeToEdge(findViewById(android.R.id.content))
    }
}