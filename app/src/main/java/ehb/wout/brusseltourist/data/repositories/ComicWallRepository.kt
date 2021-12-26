package ehb.wout.brusseltourist.data.repositories

import androidx.lifecycle.LiveData
import ehb.wout.brusseltourist.data.DAO.ComicWallDao
import ehb.wout.brusseltourist.data.enitities.ComicWall


class ComicWallRepository(private val comicWallDao: ComicWallDao) {
    val readAllData: List<ComicWall> = comicWallDao.readAllData()

    fun addComicWall(comicWall: ComicWall){
        comicWallDao.addComicWall(comicWall)
    }
}