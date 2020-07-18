package com.stats.umer.stats

class ConstantValues {
    companion object {
        val logInStudentUrl = "http://saalameri30892.domain.com/buslocator/student_login.php"
        val logInParentUrl = "http://saalameri30892.domain.com/buslocator/parent_login.php"
        val logInDriverUrl = "http://saalameri30892.domain.com/buslocator/driver-login.php"
        val fetchBusInfoUrl = "http://saalameri30892.domain.com/buslocator/view_bus_list.php"
        val fetchStopInfoUrl = "http://saalameri30892.domain.com/buslocator/view_stop_list.php"
        val trackStudentUrl = "http://saalameri30892.domain.com/buslocator/view-child-location.php"
        val reserveSeatUrl = "http://saalameri30892.domain.com/buslocator/reserve_seat.php"
        val gotBusUrl = "http://saalameri30892.domain.com/buslocator/got_bus.php"

        //intent extra names
        val loggedInUserInfoExtra = "loggedInUserInfoExtra"
        val stopInfoExtra = "stopInfoExtra"
        val locationActivityPreviousActivityExtra = "locationActivityPreviousActivityExtra"
        val campusToCampusShuttleExtra = "campusToCampusShuttleExtra"

        //Shared Prefrence Key
        val reservedSeatsKey = "reservedSeatsKey"
        val statsSharedPrefs = "statsSharedPrefs"

        // location activity previous activity flag
        val trackYourStudent = 1
        val driverPosition = 2
        val reserveSeat = 3
    }
}