package ehb.wout.brusseltourist.ui.Map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.mapbox.geojson.Point
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import ehb.wout.brusseltourist.R
import ehb.wout.brusseltourist.data.enitities.ComicWall
import ehb.wout.brusseltourist.data.viewModels.ComicWallViewModel
import ehb.wout.brusseltourist.data.viewModels.ComicWallViewModelFactory


private const val ARG_MAP = "map"


class MapFragment : Fragment() {
    private var mapView: MapView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mapView = view.findViewById(R.id.mapView)

        //val comicWallViewModel: ComicWallViewModel = ComicWallViewModelFactory(activity?.application!!).create(ComicWallViewModel::class.java)
        val comicWallViewModel: ComicWallViewModel = ViewModelProvider(
            this,
            ComicWallViewModelFactory(activity?.application!!)
        )[ComicWallViewModel::class.java]

        mapView?.getMapboxMap()?.loadStyleUri(Style.MAPBOX_STREETS) {
            loadAnnotations(comicWallViewModel.getComicWalls())
        }

        super.onViewCreated(view, savedInstanceState)
    }


    private fun loadAnnotations(comicWalls: List<ComicWall>) {
        Log.i("MapData", comicWalls.toString())
        for (comicWall in comicWalls) {
            addAnnotationToMap(comicWall.longitude, comicWall.latitude)
        }
    }

    private fun addAnnotationToMap(longitude: Double, latitude: Double) {
        // Create an instance of the Annotation API and get the PointAnnotationManager.
        bitmapFromDrawableRes(
            activity?.applicationContext!!,
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