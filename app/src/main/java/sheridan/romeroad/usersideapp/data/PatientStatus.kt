package sheridan.romeroad.usersideapp.data

/**
 * Student ID: 991555778
 * UserSideApp
 * created by davidromero
 * on 2024-11-28
 **/
data class PatientStatus(
    val healthStatus: String = "", // e.g., "Diabetes"
    val medications: List<MedicationReminder> = emptyList(),
    val avgBloodOxygen: Int = 0, // Percentage
    val avgHeartRate: Int = 0, // Beats per minute
    val avgStepsPerDay: Int = 0,
    val emergencyContact: String = "", // Contact info
    val nurseNotes: String = "" // Notes from the nurse
)
