package ehb.wout.brusseltourist.data.enitities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comic_wall_table")
data class ComicWall(
    @PrimaryKey val comicWallId: String,
    val author: String,
    val longitude: Double,
    val latitude: Double,
    val personages: String,
    @Embedded val photo: Photo?
)