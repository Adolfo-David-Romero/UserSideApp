package sheridan.romeroad.usersideapp.ui.status

/**
 * Student ID: 991555778
 * UserSideApp
 * created by davidromero
 * on 2024-11-28
 **/
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import sheridan.romeroad.usersideapp.data.MedicationReminder
import sheridan.romeroad.usersideapp.ui.medication.MedicationReminderItem
import sheridan.romeroad.usersideapp.viewmodels.MedicationViewModel
import sheridan.romeroad.usersideapp.viewmodels.PatientStatusViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PatientStatusScreen(
    viewModel: PatientStatusViewModel,
    medicationViewModel: MedicationViewModel,
    patientId: String
) {
    val patientStatus by viewModel.patientStatus.collectAsState()
    val medications by medicationViewModel.medications.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchPatientStatus(patientId)
        medicationViewModel.fetchMedications()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Patient Status",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header Section
            item {
                Text(
                    "Patient Overview",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            // General Health Information
            patientStatus?.let { status ->
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text("Health Status: ${status.healthStatus}", style = MaterialTheme.typography.bodyLarge)
                            Text("Avg. Blood Oxygen: ${status.avgBloodOxygen}%", style = MaterialTheme.typography.bodyLarge)
                            Text("Avg. Heart Rate: ${status.avgHeartRate} BPM", style = MaterialTheme.typography.bodyLarge)
                            Text("Avg. Steps Per Day: ${status.avgStepsPerDay}", style = MaterialTheme.typography.bodyLarge)
                            Text("Emergency Contact: ${status.emergencyContact}", style = MaterialTheme.typography.bodyLarge)
                            Text("Nurse Notes: ${status.nurseNotes}", style = MaterialTheme.typography.bodyLarge)
                        }
                    }
                }

                // Medications Section Header
                item {
                    Text(
                        "Medications",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                // Medications List
                if (medications.isNotEmpty()) {
                    items(medications) { medication ->
                        MedicationReminderItem(medication)
                    }
                } else {
                    item {
                        Text(
                            "No medications available.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } ?: run {
                item {
                    Text(
                        "Loading patient status...",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
