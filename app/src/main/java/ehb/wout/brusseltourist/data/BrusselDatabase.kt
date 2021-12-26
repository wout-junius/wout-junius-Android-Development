package ehb.wout.brusseltourist.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ehb.wout.brusseltourist.data.DAO.ComicWallDao
import ehb.wout.brusseltourist.data.enitities.ComicWall
import ehb.wout.brusseltourist.data.enitities.Photo

@Database(entities = [ComicWall::class, Photo::class], version = 1, exportSchema = false)
abstract class BrusselDatabase : RoomDatabase() {

    abstract fun comicWallDao(): ComicWallDao

    companion object {

        private var INSTANCE: BrusselDatabase? = null

        fun getDatabase(context: Context): BrusselDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BrusselDatabase::class.java,
                    "brussel_database"
                ).allowMainThreadQueries().build()
                INSTANCE = instance
                return instance
            }
        }


    }
}