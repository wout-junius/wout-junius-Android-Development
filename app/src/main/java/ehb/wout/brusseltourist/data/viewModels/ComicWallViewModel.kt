package ehb.wout.brusseltourist.data.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import ehb.wout.brusseltourist.data.BrusselDatabase
import ehb.wout.brusseltourist.data.enitities.ComicWall
import ehb.wout.brusseltourist.data.enitities.Photo
import ehb.wout.brusseltourist.data.repositories.ComicWallRepository
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class ComicWallViewModel(application: Application): ViewModel() {
    private var comicWallRepository: ComicWallRepository? = null
    private var db: BrusselDatabase? = null
    private var queue: RequestQueue? = null

    init {
        db = BrusselDatabase.getDatabase(application)
        comicWallRepository = ComicWallRepository(db!!.comicWallDao())
        queue = Volley.newRequestQueue(application)
    }
    private val comicWalls: List<ComicWall> = loadComicWalls()

    fun getComicWalls(): List<ComicWall> {
        return comicWalls
    }

    private fun loadComicWalls(): List<ComicWall> {
        var data = comicWallRepository!!.readAllData
        if(data.isEmpty()){
            loadInAPIData()
            do {
                data = ComicWallRepository(db!!.comicWallDao()).readAllData
            }while (data.isNotEmpty())

        }
        return data
    }

    private fun loadInAPIData(start: Int = 0) {
        // Instantiate the RequestQueue.

        val url =
            "https://bruxellesdata.opendatasoft.com/api/records/1.0/search/?dataset=comic-book-route&rows=20&start=$start"
        //Make session
        val stringRequest2 = JsonObjectRequest(
            Request.Method.GET, "https://bruxellesdata.opendatasoft.com", null, null, null)
            // Request a string response from the provided URL.
            val stringRequest = JsonObjectRequest(
                Request.Method.GET, url, null, { response ->
                    if(response.getJSONArray("records").length() == 0) return@JsonObjectRequest
                    loadData(response.getJSONArray("records"))
                    loadInAPIData(start + 20)

                },
                { error ->
                    Log.e("Error", error.toString())
                    throw error
                })

            // Add the request to the RequestQueue.
            queue!!.add(stringRequest2)
            queue!!.add(stringRequest)

    }

    private fun loadData(APIData: JSONArray) {
        for (number in 0 until APIData.length()-1) run {
            val comicWall = parseComicWall(APIData.getJSONObject(number))
            comicWallRepository!!.addComicWall(comicWall)
        }
    }

    private fun parseComicWall(comicWallJson: JSONObject): ComicWall {
        var photo: Photo?
        val fields = comicWallJson.getJSONObject("fields")
        try{
            photo = parsePhoto(fields.getJSONObject("photo"))
        } catch (exception: JSONException){
            photo = null
        }
        return ComicWall(
            comicWallJson.getString("recordid"),
            fields.getString("auteur_s"),
            fields.getJSONArray("coordonnees_geographiques").getDouble(1),
            fields.getJSONArray("coordonnees_geographiques").getDouble(0),
            fields.getString("personnage_s"),
            photo
        )
    }

    private fun parsePhoto(photoJson: JSONObject): Photo {

        return Photo(
            photoJson.getString("id"),
            photoJson.getString("filename"),
            photoJson.getInt("height"),
            photoJson.getInt("width"),
            photoJson.getBoolean("thumbnail")
        )
    }

}

class ComicWallViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ComicWallViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ComicWallViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}