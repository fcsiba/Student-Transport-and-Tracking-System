package com.stats.umer.stats.model

class LoginResponseDTO {
    // Setter Methods
    // Getter Methods
    var status: String = ""
    var message: String = ""
    var data: LoginUserDataNode = LoginUserDataNode()

}

class LoginUserDataNode {
    // Setter Methods
    // Getter Methods
    var id: String = ""
    var name: String = ""
    var password: String = ""
    var father_name: String = ""
    var longitude: String = ""
    var latitude: String = ""
    var cnic: String = ""
    var contact_no: String = ""
    var address: String = ""
    var father_contact_no: String = ""
    var type: String = ""
    var status:Int = 1

}