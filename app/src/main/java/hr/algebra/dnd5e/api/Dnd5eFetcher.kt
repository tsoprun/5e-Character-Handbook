package hr.algebra.dnd5e.api

import android.content.ContentValues
import android.content.Context
import hr.algebra.dnd5e.CLASSES_CONTENT_URI
import hr.algebra.dnd5e.SKILLS_CONTENT_URI
import hr.algebra.dnd5e.SUBCLASSES_CONTENT_URI
import hr.algebra.dnd5e.framework.sendBroadcast
import hr.algebra.dnd5e.model.RaceRef
import hr.algebra.dnd5e.model.ClassRef
import hr.algebra.dnd5e.model.SkillRef
import hr.algebra.dnd5e.model.SubclassRef
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import hr.algebra.dnd5e.Dnd5eReceiver
import hr.algebra.dnd5e.RACES_CONTENT_URI


class Dnd5eFetcher (private val context: Context){

    private val api: Dnd5eApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        api = retrofit.create<Dnd5eApi>()
    }

    fun fetchReferenceData(){
        val scope= CoroutineScope(Dispatchers.IO)
        scope.launch {
            populateRaces(fetchRaces())

            val classes = fetchClasses()
            populateClasses(classes)

            populateSubclasses(fetchSubclasses(classes.map{it.apiIndex}))
            populateSkills(fetchSkills())
            context.sendBroadcast<Dnd5eReceiver>()
        }
    }

    //fetch
    private fun fetchRaces(): List<RaceRef> {
        val refs = api.fetchRaces().execute().body()?.results ?: return emptyList()
        return refs.mapNotNull{ref ->
            val detail = api.fetchRaceDetail(ref.index).execute().body() ?: return@mapNotNull null
            val bonuses = detail.ability_bonuses.joinToString(","){
                "${it.ability_score.index}: +${it.bonus}"
            }
            RaceRef(null, detail.index, detail.name, detail.speed, bonuses)
        }
    }


    private fun fetchClasses(): List<ClassRef> {
        val refs = api.fetchClasses().execute().body()?.results ?: return emptyList()
        return refs.mapNotNull{ref ->
            val detail = api.fetchClassDetail(ref.index).execute().body() ?: return@mapNotNull null
            val saves = detail.saving_throws.joinToString(","){it.index}
            val skillChoice = detail.proficiency_choices.firstOrNull { choice ->
                choice.from.options.any {it.item.index.startsWith("skill") }
            }
            val count = skillChoice?.choose ?: 0
            val options = skillChoice?.from?.options
                ?.map{it.item.index}
                ?.filter{it.startsWith("skill")}
                ?.joinToString(",") { it.removePrefix("skill-") }
                ?:""
            ClassRef(null, detail.index, detail.name, detail.hit_die, saves, count, options)
        }
    }


    private fun fetchSubclasses(classIndexes: List<String>): List<SubclassRef> {
        val subclasses = mutableListOf<SubclassRef>()
        classIndexes.forEach { classIndex ->
            val refs = api.fetchSubclasses(classIndex).execute().body()?.results ?: return@forEach
            refs.forEach { ref ->
                subclasses.add(SubclassRef(null, ref.index, ref.name, classIndex))
            }
        }
       return subclasses
    }

    private fun fetchSkills(): List<SkillRef> {
        val refs = api.fetchSkills().execute().body()?.results ?: return emptyList()
        return refs.mapNotNull{ref ->
            val detail = api.fetchSkillDetail(ref.index).execute().body() ?: return@mapNotNull null
            SkillRef(null, ref.index, ref.name, detail.ability_score.index)
        }
    }


    //populate

    private fun populateRaces (races: List<RaceRef>) {
        races.forEach { race ->
            val values = ContentValues().apply {
                put(RaceRef::apiIndex.name, race.apiIndex)
                put(RaceRef::name.name, race.name)
                put(RaceRef::speed.name, race.speed)
                put(RaceRef::abilityBonuses.name, race.abilityBonuses)
            }
            context.contentResolver.insert(RACES_CONTENT_URI, values)
        }
    }
    private fun populateClasses (classes: List<ClassRef>){
        classes.forEach { charClass ->
            val values = ContentValues().apply {
                put(ClassRef::apiIndex.name, charClass.apiIndex)
                put(ClassRef::name.name, charClass.name)
                put(ClassRef::hitDie.name, charClass.hitDie)
                put(ClassRef::savingThrows.name, charClass.savingThrows)
                put(ClassRef::skillChoiceCount.name, charClass.skillChoiceCount)
                put(ClassRef::skillOptions.name, charClass.skillOptions)
            }
            context.contentResolver.insert(CLASSES_CONTENT_URI, values)
        }
    }

    private fun populateSubclasses(subclasses: List<SubclassRef>){
        subclasses.forEach { subclass ->
            val values = ContentValues().apply {
                put(SubclassRef::apiIndex.name, subclass.apiIndex)
                put(SubclassRef::name.name, subclass.name)
                put(SubclassRef::classIndex.name, subclass.classIndex)
            }
            context.contentResolver.insert(SUBCLASSES_CONTENT_URI, values)
            }
    }

    private fun populateSkills(skills: List<SkillRef>){
        skills.forEach { skill ->
            val values = ContentValues().apply {
                put(SkillRef::apiIndex.name, skill.apiIndex)
                put(SkillRef::name.name, skill.name)
                put(SkillRef::ability.name, skill.ability)
            }
            context.contentResolver.insert(SKILLS_CONTENT_URI, values)
        }
    }




}