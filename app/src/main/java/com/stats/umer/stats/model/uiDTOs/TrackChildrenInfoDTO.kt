package com.stats.umer.stats.model.uiDTOs

import com.google.android.gms.maps.model.LatLng

class TrackChildrenInfoDTO {
    constructor()
    constructor(pos: LatLng, busNo: String)
    {
        this.position = pos
        this.busNumber = busNo
    }
    var position: LatLng = LatLng(0.0, 0.0)
    var busNumber: String = ""
}