package sheridan.romeroad.usersideapp.ui.medication

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Student ID: 991555778
 * UserSideApp
 * created by davidromero
 * on 2024-11-27
 **/
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicationRemindersScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Medication Reminders") },
                //backgroundColor = MaterialTheme.colors.primary
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Upcoming Medications",
                style = MaterialTheme.typography.labelLarge
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(getMedicationReminders()) { reminder ->
                    MedicationReminderItem(reminder)
                }
            }
        }
    }
}

@Composable
fun MedicationReminderItem(reminder: MedicationReminder) {
    Card(
        modifier = Modifier.fillMaxWidth(),

    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = reminder.name, style = MaterialTheme.typography.bodyMedium)
                Text(text = "Time: ${reminder.time}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Dosage: ${reminder.dosage}", style = MaterialTheme.typography.bodyMedium)
            }
            Checkbox(checked = reminder.isTaken, onCheckedChange = null)
        }
    }
}

// Mock data
data class MedicationReminder(
    val name: String,
    val time: String,
    val dosage: String,
    val isTaken: Boolean
)

fun getMedicationReminders() = listOf(
    MedicationReminder("Aspirin", "8:00 AM", "100 mg", false),
    MedicationReminder("Vitamin D", "1:00 PM", "200 IU", true),
    MedicationReminder("Antibiotics", "6:00 PM", "500 mg", false)
)
