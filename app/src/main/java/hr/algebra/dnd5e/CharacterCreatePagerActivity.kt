package hr.algebra.dnd5e

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.viewpager2.widget.ViewPager2
import hr.algebra.dnd5e.adapter.CharacterCreatePagerAdapter
import hr.algebra.dnd5e.databinding.ActivityCharacterCreatePagerBinding
import hr.algebra.dnd5e.model.Ability
import hr.algebra.dnd5e.model.AbilityScore

private const val KEY_RACE = "race"
private const val KEY_RACE_SPEED = "race_speed"
private const val KEY_CLASS = "class"
private const val KEY_CLASS_HIT_DIE = "class_hit_die"
private const val KEY_CLASS_INDEX = "class_index"
private const val KEY_SUBCLASS = "subclass"
private const val KEY_SCORES = "scores"
private const val KEY_SKILLS = "skills"

class CharacterCreatePagerActivity : AppCompatActivity() {


    private lateinit var binding: ActivityCharacterCreatePagerBinding



    var selectedRace: String?=null
    var selectedRaceSpeed: Int = 0
    var selectedClass: String?=null
    var selectedClassHitDie: Int =0
    var selectedClassIndex: String?=null
    var selectedSubclass: String?=null
    val abilityScores = Ability.values().map { AbilityScore(it, 8) }.toMutableList()
    val selectedSkills = mutableSetOf<String>()





    private var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterCreatePagerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        savedInstanceState?.let{restoreState(it)}
        init()
        initPager()
        initBottomNav()
    }

    private fun init() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        WindowCompat.getInsetsController(window, binding.root)
            .isAppearanceLightStatusBars = true
        applyStatusBarInset()

    }

    private fun applyStatusBarInset() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.toolbar) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updatePadding(top = bars.top)
            insets
        }
    }

    private fun initPager() {
        binding.viewPager2.adapter = CharacterCreatePagerAdapter(this, position)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

    private val navIds = listOf(

        R.id.menuRace,
        R.id.menuClass,
        R.id.menuSubclass,
        R.id.menuAbilities,
        R.id.menuSkillStep,
        R.id.menuFinish)

    private fun initBottomNav(){
        binding.bottomNav.setOnItemSelectedListener {  item ->
            binding.viewPager2.currentItem = navIds.indexOf(item.itemId)
            true
        }
        binding.viewPager2.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(pos: Int) {
                    binding.bottomNav.selectedItemId = navIds[pos]
                }
            }
        )

        binding.viewPager2.setCurrentItem(0, false)
        binding.bottomNav.selectedItemId = R.id.menuRace
    }

    fun applyRatialBonuses(raw: String){
        abilityScores.forEach{it.bonus=0}
        raw.split(",").forEach{part->
            val entry = part.trim()
            if (entry.isEmpty()) return@forEach
            val(key,amount)=entry.split(":")
            val ability=Ability.valueOf(key.trim().uppercase())
            abilityScores.firstOrNull{it.ability==ability}?.bonus=
                amount.trim().removePrefix("+").toInt()
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_RACE, selectedRace)
        outState.putInt(KEY_RACE_SPEED, selectedRaceSpeed)
        outState.putString(KEY_CLASS, selectedClass)
        outState.putInt(KEY_CLASS_HIT_DIE, selectedClassHitDie)
        outState.putString(KEY_CLASS_INDEX, selectedClassIndex)
        outState.putString(KEY_SUBCLASS, selectedSubclass)
        outState.putIntArray(KEY_SCORES, abilityScores.flatMap {
            listOf(it.value, it.bonus)
        }.toIntArray())
        outState.putStringArray(KEY_SKILLS, selectedSkills.toTypedArray())
    }

    private fun restoreState(state: Bundle) {
        selectedRace = state.getString(KEY_RACE)
        selectedRaceSpeed = state.getInt(KEY_RACE_SPEED)
        selectedClass = state.getString(KEY_CLASS)
        selectedClassHitDie = state.getInt(KEY_CLASS_HIT_DIE)
        selectedClassIndex = state.getString(KEY_CLASS_INDEX)
        selectedSubclass = state.getString(KEY_SUBCLASS)

        state.getIntArray(KEY_SCORES)?.let { flat ->
            abilityScores.forEachIndexed { i, score ->
                score.value = flat[i * 2]
                score.bonus = flat[i * 2 + 1]
            }
        }
        selectedSkills.clear()
        state.getStringArray(KEY_SKILLS)?.let { selectedSkills.addAll(it) }
    }


}

