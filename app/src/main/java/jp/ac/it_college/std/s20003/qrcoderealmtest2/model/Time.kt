package jp.ac.it_college.std.s20003.qrcoderealmtest2.model

import io.realm.kotlin.types.ObjectId
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import java.time.LocalTime

class Time : RealmObject {
    @PrimaryKey
    var id: ObjectId = ObjectId.create()
    var notifyName: String = ""
    var Hour: Int = 0
    var Minute: Int = 0
}