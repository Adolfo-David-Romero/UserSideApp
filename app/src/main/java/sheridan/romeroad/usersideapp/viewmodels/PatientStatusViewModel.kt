package sheridan.romeroad.usersideapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import sheridan.romeroad.usersideapp.data.MedicationReminder
import sheridan.romeroad.usersideapp.data.PatientStatus

/**
 * Student ID: 991555778
 * UserSideApp
 * created by davidromero
 * on 2024-11-28
 **/


class PatientStatusViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    private val _patientStatus = MutableStateFlow<PatientStatus?>(null)
    val patientStatus: StateFlow<PatientStatus?> = _patientStatus

    fun fetchPatientStatus(patientId: String) {
        viewModelScope.launch {
            try {
                //TODO: Replace with medication viewmodel
                // Fetch Medications
                val medications = db.collection("medications")
                    .whereEqualTo("patientId", patientId)
                    .get()
                    .await()
                    .documents.mapNotNull { it.toObject(MedicationReminder::class.java) }

                // Populate the patient status
                val fetchedStatus = PatientStatus(
                    healthStatus = "Diabetes", // Placeholder
                    medications = medications,
                    avgBloodOxygen = 98, // Placeholder
                    avgHeartRate = 72, // Placeholder
                    avgStepsPerDay = 5000, // Placeholder
                    emergencyContact = "John Doe: +1234567890", // Placeholder
                    nurseNotes = "Patient is recovering well." // Placeholder
                )

                _patientStatus.value = fetchedStatus
            } catch (e: Exception) {
                _patientStatus.value = null
            }
        }
    }
}
