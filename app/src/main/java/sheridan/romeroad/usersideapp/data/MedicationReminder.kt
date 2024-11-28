package sheridan.romeroad.usersideapp.data

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
/**
 * Student ID: 991555778
 * UserSideApp
 * created by davidromero
 * on 2024-11-28
 **/
data class MedicationReminder(
    val id: String = "",
    val name: String = "",
    val time: String = "",
    val dosage: String = "",
    val isTaken: Boolean = false
)