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
        //TODO
//        val call = client.newCall(
//            Request.Builder().url("https://eltex-android.ru/api/events/$id/participants").post(
//                "".toRequestBody(jsonType)
//            ).build()
//        )
//
//        call.enqueue(object : okhttp3.Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                callback.onError(e)
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                if (response.isSuccessful) {
//                    try {
//                        callback.onSuccess(
//                            json.decodeFromString(requireNotNull(response.body).string())
//                        )
//                    } catch (e: Exception) {
//                        callback.onError(e)
//                    }
//
//                } else {
//                    callback.onError(RuntimeException("Response code is ${response.code}"))
//
//                }
//            }
//        })
    }

    override fun deleteParticipateById(id: Long, callback: DomainCallback<Event>) {
        //TODO
//        val call = client.newCall(
//            Request.Builder().url("https://eltex-android.ru/api/events/$id/participants").delete(
//                "".toRequestBody(jsonType)
//            ).build()
//        )
//
//        call.enqueue(object : okhttp3.Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                callback.onError(e)
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                if (response.isSuccessful) {
//                    try {
//                        callback.onSuccess(
//                            json.decodeFromString(requireNotNull(response.body).string())
//                        )
//                    } catch (e: Exception) {
//                        callback.onError(e)
//                    }
//
//                } else {
//                    callback.onError(RuntimeException("Response code is ${response.code}"))
//
//                }
//            }
//        })
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
        //TODO
//        val call = client.newCall(
//            Request.Builder().url("https://eltex-android.ru/api/events/$id")
//                .delete("".toRequestBody(jsonType)).build()
//        )
//
//        call.enqueue(object : okhttp3.Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                callback.onError(e)
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                if (response.isSuccessful) {
//                    try {
//                        callback.onSuccess(Unit)
//                    } catch (e: Exception) {
//                        callback.onError(e)
//                    }
//
//                } else {
//                    callback.onError(RuntimeException("Response code is ${response.code}"))
//
//                }
//            }
//        })
    }
}