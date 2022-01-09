package ehb.wout.brusseltourist.ui.List

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import androidx.lifecycle.ViewModelProvider
import ehb.wout.brusseltourist.DetailedActivity
import ehb.wout.brusseltourist.R
import ehb.wout.brusseltourist.data.enitities.ComicWall
import ehb.wout.brusseltourist.data.viewModels.ComicWallViewModel
import ehb.wout.brusseltourist.data.viewModels.ComicWallViewModelFactory
/**
 * A fragment representing a list of Items.
 */
class ComicWallItemFragment : Fragment() {
    private var comicWallViewModel: ComicWallViewModel? = null

    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        comicWallViewModel = ViewModelProvider(
            this@ComicWallItemFragment,
            ComicWallViewModelFactory(activity?.application!!)
        )[ComicWallViewModel::class.java]

        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = comicWallViewModel!!.getComicWalls().size
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_comic_wall_item_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {

            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }

                adapter = ComicWallRecyclerViewAdapter(comicWallViewModel!!.getComicWalls()
                ) {
                    onclick(it)
                }
            }
        }
        return view
    }

    private fun onclick(comicwall:ComicWall){
        val intent = Intent(activity?.applicationContext!!, DetailedActivity::class.java).apply {
            putExtra("EXTRA_MESSAGE", comicwall)
        }
        startActivity(intent)
        Log.i("Click Listener", "Clicked!")
    }
}