package hr.algebra.dnd5e.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

const val API_URL = "https://www.dnd5eapi.co/api/"

interface Dnd5eApi {
    @GET("races")
    fun fetchRaces(): Call<ReferenceList>

    @GET("classes")
    fun fetchClasses(): Call<ReferenceList>

    @GET("skills")
    fun fetchSkills(): Call<ReferenceList>

    @GET("classes/{index}/subclasses")
    fun fetchSubclasses(@Path("index") index: String): Call<ReferenceList>

    @GET ("races/{index}")
    fun fetchRaceDetail(@Path("index") index: String): Call<RaceDetail>

    @GET ("classes/{index}")
    fun fetchClassDetail(@Path("index") index: String): Call<ClassDetail>

    @GET ("skills/{index}")
    fun fetchSkillDetail(@Path("index") index: String): Call<SkillDetail>

}