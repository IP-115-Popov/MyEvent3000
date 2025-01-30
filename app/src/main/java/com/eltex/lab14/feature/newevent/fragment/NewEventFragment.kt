package com.eltex.lab14.feature.newevent.fragment

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.core.view.isVisible
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
import com.eltex.lab14.BuildConfig
import com.eltex.lab14.R
import com.eltex.lab14.databinding.FragmentNewPostBinding
import com.eltex.lab14.feature.events.data.AttachmentType
import com.eltex.lab14.feature.events.repository.NetworkEventsRepository
import com.eltex.lab14.feature.newevent.viewmodel.FileModel
import com.eltex.lab14.feature.newevent.viewmodel.NewEventViewModel
import com.eltex.lab14.feature.toolbar.viewmodel.ToolbarViewModel
import com.eltex.lab14.util.getErrorText
import com.eltex.lab14.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.withCreationCallback
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class NewEventFragment : Fragment() {
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

        val newEventViewModel by viewModels<NewEventViewModel>(
            extrasProducer = {
                defaultViewModelCreationExtras.withCreationCallback<NewEventViewModel.ViewModelFactory> {
                    factory ->
                    factory.create(postId)
                }
            }
        )

        val photoUri = getPhotoUri()

        val takePictureContract = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                newEventViewModel.saveAttachment(FileModel(photoUri, AttachmentType.IMAGE))
            }
        }

        val galleryContract = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
           uri?.let {
               newEventViewModel.saveAttachment(FileModel(it, AttachmentType.IMAGE))
           }
        }

        toolbarViewModel.saveClicked.filter { it }.onEach {
            val content = binding.etvContent.text?.toString().orEmpty()

            if (content.isNotBlank()) {
                newEventViewModel.save(content)
                // findNavController().navigateUp()
            } else {
                requireContext().toast(R.string.content_is_epmty, false)
            }
            toolbarViewModel.onSaveClicked(false)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        observeState(newEventViewModel, binding)

        binding.takePhoto.setOnClickListener {
            takePictureContract.launch(photoUri)
        }

        binding.remove.setOnClickListener {
            newEventViewModel.saveAttachment(null)
        }

        binding.gallery.setOnClickListener {
            galleryContract.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

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

    private fun observeState(
        newPostViewModel: NewEventViewModel,
        binding: FragmentNewPostBinding
    ) {
        newPostViewModel.state
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
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

                when (it.file?.type) {
                    AttachmentType.IMAGE -> {
                        binding.photoContainer.isVisible = true
                        binding.photo.setImageURI(it.file.uri)
                    }

                    AttachmentType.VIDEO,
                    AttachmentType.AUDIO,
                    null -> binding.photoContainer.isGone = true
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun getPhotoUri(): Uri {
        val directory = requireContext().cacheDir.resolve("file_picker").apply {
                mkdirs()
        }

        val file = directory.resolve("image.png")

        return FileProvider.getUriForFile(
            requireContext(),
            "${BuildConfig.APPLICATION_ID}.fileprovider",
            file
        )
    }
}