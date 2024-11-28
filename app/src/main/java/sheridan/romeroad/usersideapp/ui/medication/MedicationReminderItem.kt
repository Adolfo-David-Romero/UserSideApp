package sheridan.romeroad.usersideapp.ui.medication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import sheridan.romeroad.usersideapp.data.MedicationReminder

/**
 * Student ID: 991555778
 * UserSideApp
 * created by davidromero
 * on 2024-11-28
 **/
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