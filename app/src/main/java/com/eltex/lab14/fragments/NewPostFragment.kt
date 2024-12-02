package com.eltex.lab14.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.findNavController
import com.eltex.lab14.R
import com.eltex.lab14.databinding.FragmentNewPostBinding
import com.eltex.lab14.db.AppDb
import com.eltex.lab14.presentation.viewmodel.NewPostViewModel
import com.eltex.lab14.presentation.viewmodel.ToolbarViewModel
import com.eltex.lab14.repository.SqliteEventsRepository
import com.eltex.lab14.utils.toast
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class NewPostFragment : Fragment() {


    companion object {
        const val POST_ID = "POST_ID"
        const val CONTENT = "CONTENT"
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
                        SqliteEventsRepository(
                            AppDb.getInstance(requireContext().applicationContext).eventDao
                        ), postId
                    )
                }
            }
        }
        toolbarViewModel.saveClicked.filter { it }.onEach {
            val content = binding.etvContent.text?.toString().orEmpty()

            if (content.isNotBlank()) {
                newPostViewModel.save(content)
                findNavController().navigateUp()
            } else {
                requireContext().toast(R.string.content_is_epmty, false)
            }
            toolbarViewModel.onSaveClicked(false)
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
//        val id = intent.getLongExtra(Intent.EXTRA_INDEX, -1)
//        val oldContent: String = intent.getStringExtra(Intent.EXTRA_TEXT) ?: ""
//
//        binding.etvContent.setText(Editable.Factory.getInstance().newEditable(oldContent))
//
//        binding.toolbar.menu.findItem(R.id.save_post).setOnMenuItemClickListener {
//            if (id == Constants.ID_NON_EXISTENT_EVENT) addEvent()
//            else updatePost(id)
//
//            true
//        }
//
//        binding.toolbar.setNavigationOnClickListener {
//            onBackPressed()
//        }

        return binding.root
    }
}