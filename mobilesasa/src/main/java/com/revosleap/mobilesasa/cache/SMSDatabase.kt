package com.revosleap.mobilesasa.cache

import android.content.Context
import com.revosleap.mobilesasa.models.SMS
import com.revosleap.mobilesasa.utils.Commons

@Database(entities = [SMS::class], version = 1, exportSchema = false)
abstract class SMSDatabase : RoomDatabase() {
    abstract fun smsDao(): SMSDao

    companion object {
        private var db: SMSDatabase? = null
        operator fun invoke(context: Context) = db ?: synchronized(LOCK) {
            db ?: build(context).also { db = it }
        }

        private fun build(context: Context) =
            Room.databaseBuilder(context, SMS::class.java, Commons.DB_NAME)
                .build()
    }
}