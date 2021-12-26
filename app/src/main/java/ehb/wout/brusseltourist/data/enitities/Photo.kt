package ehb.wout.brusseltourist.data.enitities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Photo(

    @PrimaryKey @ColumnInfo(name = "photo_id") val photoId: Int,
    @ColumnInfo(name = "filename") val filename: String,
    @ColumnInfo(name = "height") val height: Int,
    @ColumnInfo(name = "width") val width: Int,
    @ColumnInfo(name = "thumbnail") val thumbnail: Boolean,
)