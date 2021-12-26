package ehb.wout.brusseltourist.data.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ehb.wout.brusseltourist.data.enitities.ComicWall

@Dao
interface ComicWallDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addComicWall(comicWall: ComicWall)

    @Query("SELECT * FROM comic_wall_table")
    fun readAllData(): List<ComicWall>

}