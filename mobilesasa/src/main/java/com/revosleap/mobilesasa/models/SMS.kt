package com.revosleap.mobilesasa.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.revosleap.mobilesasa.utils.Commons

@Entity(tableName = Commons.DB_NAME)
data class SMS(
    @PrimaryKey(autoGenerate = true)
    var smsId: String? = null,
    var recipient: String? = null,
    var message: String? = null
)