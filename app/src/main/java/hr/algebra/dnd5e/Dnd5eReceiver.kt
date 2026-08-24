package hr.algebra.dnd5e

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import hr.algebra.dnd5e.framework.setBooleanPreference
import hr.algebra.dnd5e.framework.startActivity

class Dnd5eReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        context.setBooleanPreference(DATA_IMPORTED)
        context.startActivity<HostActivity>()
    }
}