package ehb.wout.brusseltourist

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
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
import kotlin.system.*

class MainActivity : AppCompatActivity() {

    private var mapView: MapView? = null
    private var comicWallRepository: ComicWallRepository? = null
    private var comicWalls: List<ComicWall>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        val db: BrusselDatabase = BrusselDatabase.getDatabase(applicationContext)
        comicWallRepository = ComicWallRepository(db.comicWallDao())
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        comicWalls = comicWallRepository!!.readAllData
        //check if there is daa
        if(comicWalls == null){

            comicWallRepository!!.addComicWall(ComicWall(
                1,
                "Test",
                42.5,32.3,
                "test",
                Photo(
                    1,
                    "test.png",13,13,true
                    )
                )
            )

        }


        mapView = findViewById(R.id.mapView)
        mapView?.getMapboxMap()?.loadStyleUri(Style.MAPBOX_STREETS
        ) { addAnnotationToMap(comicWalls!![0].longitude, comicWalls!![0].latitude) }
    }

//  private fun loadInAPIData(){
//        // Instantiate the RequestQueue.
//        val queue = Volley.newRequestQueue(this)
//        val url = "https://www.google.com"
//
//// Request a string response from the provided URL.
//        val stringRequest = StringRequest(Request.Method.GET, url,
//            Response.Listener<String> { response ->
//                // Display the first 500 characters of the response string.
//                textView.text = "Response is: ${response.substring(0, 500)}"
//            },
//            Response.ErrorListener { textView.text = "That didn't work!" })
//
//// Add the request to the RequestQueue.
//        queue.add(stringRequest)
//  }


    //MapBox Map
    private fun addAnnotationToMap(longitude: Double, latitude: Double) {
        // Create an instance of the Annotation API and get the PointAnnotationManager.
        bitmapFromDrawableRes(
            this@MainActivity,
            R.drawable.red_marker
        )?.let {
            val annotationApi = mapView?.annotations
            val pointAnnotationManager = annotationApi?.createPointAnnotationManager()
            // Set options for the resulting symbol layer.
            val pointAnnotationOptions: PointAnnotationOptions = PointAnnotationOptions()
                // Define a geographic coordinate.
                .withPoint(Point.fromLngLat(longitude, latitude))
                // Specify the bitmap you assigned to the point annotation
                // The bitmap will be added to map style automatically.
                .withIconImage(it)
                // Add the resulting pointAnnotation to the map.
            pointAnnotationManager?.create(pointAnnotationOptions)
        }
    }

    private fun bitmapFromDrawableRes(context: Context, @DrawableRes resourceId: Int) =
        convertDrawableToBitmap(AppCompatResources.getDrawable(context, resourceId))

    private fun convertDrawableToBitmap(sourceDrawable: Drawable?): Bitmap? {
        if (sourceDrawable == null) {
            return null
        }
        return if (sourceDrawable is BitmapDrawable) {
            sourceDrawable.bitmap
        } else {
            // copying drawable object to not manipulate on the same reference
            val constantState = sourceDrawable.constantState ?: return null
            val drawable = constantState.newDrawable().mutate()
            val bitmap: Bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth, drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            bitmap
        }
    }
}