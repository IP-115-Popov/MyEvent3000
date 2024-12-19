package com.eltex.lab14.repository

import com.eltex.lab14.api.EventApi
import com.eltex.lab14.data.Event
import retrofit2.Call
import retrofit2.Response
import com.eltex.lab14.util.Callback as DomainCallback
import retrofit2.Callback as RetrofitCallback

class NetworkEventsRepository() : EventRepository {

    override fun getEvent(callback: DomainCallback<List<Event>>) {
        val call = EventApi.INSTANCE.getAll()

        call.enqueue(object : RetrofitCallback<List<Event>> {
            override fun onResponse(call: Call<List<Event>>, response: Response<List<Event>>) {
                if (response.isSuccessful) {
                    callback.onSuccess(
                        requireNotNull(response.body())
                    )
                } else {
                    callback.onError(RuntimeException("Response code is ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<List<Event>>, throwable: Throwable) {
                callback.onError(throwable)
            }

        })
    }

    override fun likeById(id: Long, callback: DomainCallback<Event>) {

        val call = EventApi.INSTANCE.likeById(id)

        call.enqueue(object : RetrofitCallback<Event> {
            override fun onResponse(call: Call<Event>, response: Response<Event>) {
                if (response.isSuccessful) {
                    callback.onSuccess(
                        requireNotNull(response.body())
                    )
                } else {
                    callback.onError(RuntimeException("Response code is ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<Event>, throwable: Throwable) {
                callback.onError(throwable)
            }

        })
    }

    override fun deleteLikeById(id: Long, callback: DomainCallback<Event>) {
        val call = EventApi.INSTANCE.deleteLikeById(id)

        call.enqueue(object : RetrofitCallback<Event> {
            override fun onResponse(call: Call<Event>, response: Response<Event>) {
                if (response.isSuccessful) {
                    callback.onSuccess(
                        requireNotNull(response.body())
                    )
                } else {
                    callback.onError(RuntimeException("Response code is ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<Event>, throwable: Throwable) {
                callback.onError(throwable)
            }

        })
    }

    override fun participateById(id: Long, callback: DomainCallback<Event>) {
        val call = EventApi.INSTANCE.participateById(id)

        call.enqueue(object : RetrofitCallback<Event> {
            override fun onResponse(call: Call<Event>, response: Response<Event>) {
                if (response.isSuccessful) {
                    callback.onSuccess(
                        requireNotNull(response.body())
                    )
                } else {
                    callback.onError(RuntimeException("Response code is ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<Event>, throwable: Throwable) {
                callback.onError(throwable)
            }

        })
    }

    override fun deleteParticipateById(id: Long, callback: DomainCallback<Event>) {
        val call = EventApi.INSTANCE.deleteParticipateById(id)

        call.enqueue(object : RetrofitCallback<Event> {
            override fun onResponse(call: Call<Event>, response: Response<Event>) {
                if (response.isSuccessful) {
                    callback.onSuccess(
                        requireNotNull(response.body())
                    )
                } else {
                    callback.onError(RuntimeException("Response code is ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<Event>, throwable: Throwable) {
                callback.onError(throwable)
            }

        })
    }

    override fun save(id: Long, content: String, callback: DomainCallback<Event>) {
        val call = EventApi.INSTANCE.savEvent(
            Event(
                id = id, content = content, datetime = "2024-12-18T16:07:31.146Z"
            )
        )

        call.enqueue(object : RetrofitCallback<Event> {
            override fun onResponse(call: Call<Event>, response: Response<Event>) {
                if (response.isSuccessful) {
                    callback.onSuccess(
                        requireNotNull(response.body())
                    )
                } else {
                    callback.onError(RuntimeException("Response code is ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<Event>, throwable: Throwable) {
                callback.onError(throwable)
            }

        })
    }

    override fun deleteById(id: Long, callback: DomainCallback<Unit>) {
        val call = EventApi.INSTANCE.deleteById(id)

        call.enqueue(object : RetrofitCallback<Unit> {

            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (response.isSuccessful) {
                    callback.onSuccess(Unit)
                } else {
                    callback.onError(RuntimeException("Response code is ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<Unit>, throwable: Throwable) {
                callback.onError(throwable)
            }
        })
    }
}