package ehb.wout.brusseltourist.data.DAO

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import ehb.wout.brusseltourist.data.BrusselDatabase
import ehb.wout.brusseltourist.data.enitities.ComicWall
import ehb.wout.brusseltourist.data.enitities.Photo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class CommicWallDaoTest {

    private lateinit var database: BrusselDatabase
    private lateinit var dao: ComicWallDao

    @Before
    fun setup(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            BrusselDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.comicWallDao()
    }

    @Test
    fun insertComicWall() {
        val comicWall = ComicWall("abc", "Wout", 40.0, 25.0, "School", null)
        dao.addComicWall(comicWall)

        val comicWalls = dao.readAllData()
       assertThat(comicWalls).contains(comicWall)
    }

    @After
    fun teardown(){
        database.close()
    }
}