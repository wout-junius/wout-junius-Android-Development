package ehb.wout.brusseltourist.data.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import ehb.wout.brusseltourist.data.enitities.ComicWall
import ehb.wout.brusseltourist.data.repositories.ComicWallRepository
import kotlinx.coroutines.launch

class ComicWallViewModel(private val repository: ComicWallRepository) : ViewModel() {
    val allComicWalls: List<ComicWall> = repository.readAllData

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(comicWall: ComicWall) = viewModelScope.launch {
        repository.addComicWall(comicWall)
    }
}