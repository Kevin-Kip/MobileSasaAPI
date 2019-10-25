package com.revosleap.mobilesasa.cache

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.revosleap.mobilesasa.models.SMS
import com.revosleap.mobilesasa.utils.Commons

@Dao
interface SMSDao {
    @Insert
    fun insert(vararg sms:SMS)

    @Query("SELECT * FROM ${Commons.DB_NAME}")
    fun getCached():MutableList<SMS>

    @Delete
    fun deleteOne(sms:SMS)
}