package com.stats.umer.stats

class Enum {
    enum class ServerStatusCodes constructor(val value:Int)
    {
        Success(0),
        Failure(1);
        companion object {
            fun fromString (value: String) : ServerStatusCodes
            {
                var returnResult = Failure
                when (value)
                {
                    "success" -> returnResult = Success
                    "error" -> returnResult = Failure
                }
                return returnResult
            }
        }
    }
}