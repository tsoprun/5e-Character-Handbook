package hr.algebra.dnd5e



import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import hr.algebra.dnd5e.api.Dnd5eWorker
import hr.algebra.dnd5e.databinding.ActivitySplashScreenBinding
import hr.algebra.dnd5e.framework.applyAnimation
import hr.algebra.dnd5e.framework.callDelayed
import hr.algebra.dnd5e.framework.getBooleanPreference
import hr.algebra.dnd5e.framework.isOnline
import hr.algebra.dnd5e.framework.startActivity

private const val DELAY = 3000L
const val DATA_IMPORTED = "hr.algebra.dnd5e.data_imported"
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startAnimations()
        redirect()

    }
    private fun startAnimations() {

        binding.tvSplash.applyAnimation(R.anim.blink)
        binding.ivSplash.applyAnimation(R.anim.rotate)

    }
    private fun redirect() {

        if(getBooleanPreference(DATA_IMPORTED)) {
            callDelayed(DELAY) { startActivity<HostActivity>() }
        } else {
            if(isOnline()) {
                WorkManager.getInstance(this).apply {
                    enqueueUniqueWork(
                        DATA_IMPORTED,
                        ExistingWorkPolicy.KEEP,
                        OneTimeWorkRequest.from(Dnd5eWorker::class.java)
                    )
                }
            } else {
                binding.tvSplash.text = getString(R.string.no_internet)
                callDelayed(DELAY) { finish() }
            }
        }
    }

}

