package com.eltex.lab14.feature.events.ui

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class InstantToStringConverter @Inject constructor(
    private val zoneId: ZoneId
) {
    private companion object {
        val FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm")
    }

    fun Convert(instant: Instant): String =
        InstantToStringConverter.FORMATTER.format(instant.atZone(zoneId))

}