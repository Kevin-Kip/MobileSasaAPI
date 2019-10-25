package com.revosleap.mobilesasa.cache

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.revosleap.mobilesasa.models.SMS
import com.revosleap.mobilesasa.utils.Commons

@Database(entities = [SMS::class], version = 1, exportSchema = false)
abstract class SMSDatabase : RoomDatabase() {
    abstract fun smsDao(): SMSDao

    companion object {
        private var db: SMSDatabase? = null

        fun build(context: Context?): SMSDatabase?{
            if (db == null){
                synchronized(SMSDatabase::class){
                    db = Room.databaseBuilder(context!!, SMSDatabase::class.java, Commons.DB_NAME)
                        .build()
                }
            }
            return db!!
        }
    }
}