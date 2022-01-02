package ehb.wout.brusseltourist

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.mapbox.geojson.Point
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import ehb.wout.brusseltourist.data.BrusselDatabase
import ehb.wout.brusseltourist.data.enitities.ComicWall
import ehb.wout.brusseltourist.data.enitities.Photo
import ehb.wout.brusseltourist.data.repositories.ComicWallRepository
import ehb.wout.brusseltourist.data.viewModels.ComicWallViewModel
import ehb.wout.brusseltourist.data.viewModels.ComicWallViewModelFactory
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import kotlin.system.*

class MainActivity : AppCompatActivity() {

    private var mapView: MapView? = null
    private var comicWalls: List<ComicWall>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}