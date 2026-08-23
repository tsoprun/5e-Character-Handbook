package hr.algebra.dnd5e.api

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import kotlin.jvm.Throws

class Dnd5eFetcher {

    private val api: Dnd5eApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        api = retrofit.create<Dnd5eApi>()
    }


    fun fetchRaces (onResult: (List<ApiReference>) -> Unit)=
        enqueue(api.fetchRaces(), onResult)

    fun fetchClasses(onResult: (List<ApiReference>) -> Unit) =
        enqueue(api.fetchClasses(), onResult)

    fun fetchSubclasses(classIndex: String, onResult: (List<ApiReference>) -> Unit) =
        enqueue(api.fetchSubclasses(classIndex), onResult)


    private fun enqueue(
        call: Call<ReferenceList>,
        onResult: (List<ApiReference>)->Unit
    ) {
        call.enqueue(object : Callback<ReferenceList> {
            override fun onResponse(
                call: Call<ReferenceList>,
                response: Response<ReferenceList>
            ) {
                onResult(response.body()?.results ?: emptyList())
            }

            override fun onFailure(call: Call<ReferenceList>, t: Throwable) {
                Log.e("ERROR", t.toString(), t)
                onResult(emptyList())
            }
        })
    }


}