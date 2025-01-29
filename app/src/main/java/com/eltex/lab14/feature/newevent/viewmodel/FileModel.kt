package com.eltex.lab14.feature.newevent.viewmodel

import android.net.Uri
import com.eltex.lab14.feature.events.data.AttachmentType

data class FileModel(
    val uri: Uri,
    val type: AttachmentType
)