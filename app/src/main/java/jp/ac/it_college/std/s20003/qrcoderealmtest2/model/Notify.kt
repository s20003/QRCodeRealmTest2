package jp.ac.it_college.std.s20003.qrcoderealmtest2.model

import io.realm.kotlin.types.ObjectId
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class Notify : RealmObject {
    @PrimaryKey
    var id: ObjectId = ObjectId.create()
    var notifyName: String = ""
    var time: String = ""
}