package com.stats.umer.stats.model

class BusInfoResponseDTO
{
    var status: String = ""
    var message: String = ""
    var data:Any = Any()
}

class BusInfoNodeDTO
{
    var id: String = ""
    var bus_no: String = ""
    var total_seats: String = ""
    var available_seats: String = ""
    var status: String = ""
    var current_stop: String = ""
    var next_stop: String = ""
    var type: String = ""
    var stop_id: String = ""
    var current_lat: String = ""
    var current_lng: String = ""
    var stop_name: String = ""
}