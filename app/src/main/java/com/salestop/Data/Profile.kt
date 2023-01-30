package com.salestop

data class Profile(val firstName: String?=null,
                   val lastName: String?=null,
                   val username:String?=null,
                   val birth: String?=null,
                   val gender: String?=null,
                   val password:String?=null
                    )
data class Friend(val firstName: String?=null,val lastName: String? =null)