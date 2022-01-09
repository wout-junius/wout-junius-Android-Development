package ehb.wout.brusseltourist.data.enitities

import android.os.Parcel
import android.os.Parcelable
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
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readString()!!,
        Photo(parcel.readString()!!, parcel.readString()!!, parcel.readInt(), parcel.readInt(), true)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(comicWallId)
        parcel.writeString(author)
        parcel.writeDouble(longitude)
        parcel.writeDouble(latitude)
        parcel.writeString(personages)
        parcel.writeString(photo?.photoId)
        parcel.writeString(photo?.filename)
        parcel.writeInt(photo?.height!!)
        parcel.writeInt(photo.width)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ComicWall> {
        override fun createFromParcel(parcel: Parcel): ComicWall {
            return ComicWall(parcel)
        }

        override fun newArray(size: Int): Array<ComicWall?> {
            return arrayOfNulls(size)
        }
    }
}