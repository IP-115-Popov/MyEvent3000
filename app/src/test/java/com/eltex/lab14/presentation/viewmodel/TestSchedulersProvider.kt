package com.eltex.lab14.presentation.viewmodel

import com.eltex.lab14.rx.SchedulersProvider
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

object TestSchedulersProvider: SchedulersProvider {
    override val io: Scheduler
        get() = Schedulers.trampoline()

    override val computation: Scheduler
        get() = Schedulers.trampoline()

    override val mainThread: Scheduler
        get() = Schedulers.trampoline()
}