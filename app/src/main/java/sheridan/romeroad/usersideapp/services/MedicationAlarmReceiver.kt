package sheridan.romeroad.usersideapp.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

/**
 * Student ID: 991555778
 * UserSideApp
 * created by davidromero
 * on 2024-11-28
 **/

class MedicationAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val name = intent?.getStringExtra("name")
        val time = intent?.getStringExtra("time")

        Toast.makeText(context, "Time to take your medication: $name at $time", Toast.LENGTH_LONG).show()
        // You can replace this with a system notification
    }
}
