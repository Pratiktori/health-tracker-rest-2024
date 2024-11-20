package ie.setu.domain

import org.joda.time.DateTime

data class Sleep (var id: Int,
                  var name:String,
                  var duration:Double,
                  var started:DateTime,
                  var userId:Int,

)
