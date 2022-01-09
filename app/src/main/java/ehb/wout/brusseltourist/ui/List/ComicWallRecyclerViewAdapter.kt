package ehb.wout.brusseltourist.ui.List

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import ehb.wout.brusseltourist.data.enitities.ComicWall

import ehb.wout.brusseltourist.databinding.FragmentComicWallItemBinding
import android.os.Handler
import android.os.Looper
import android.widget.AdapterView
import java.lang.Exception
import java.net.URL
import java.util.concurrent.Executors


class ComicWallRecyclerViewAdapter(
    private val values: List<ComicWall>,
    private val clickListener: (ComicWall) -> Unit
) : RecyclerView.Adapter<ComicWallRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentComicWallItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.titleView.text = item.personages
        holder.contentView.text = "By " + item.author

        /* https://www.geeksforgeeks.org/how-to-load-any-image-from-url-without-using-any-dependency-in-android/ */

        val executor = Executors.newSingleThreadExecutor()

        // Once the executor parses the URL
        // and receives the image, handler will load it
        // in the ImageView
        val handler = Handler(Looper.getMainLooper())

        // Initializing the image
        var image: Bitmap?

        // Only for Background process (can take time depending on the Internet speed)
        executor.execute {

            // Tries to get the image and post it in the ImageView
            // with the help of Handler
            try {
                if(item.photo == null) return@execute
                val url: String =
                    "https://opendata.brussel.be/explore/dataset/striproute0/files/"+ item.photo.photoId + "/download/"
                val `in` = URL(url).openStream()
                image = BitmapFactory.decodeStream(`in`)

                handler.post {
                    holder.imageView.setImageBitmap(image)
                }

            }catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun getItemCount(): Int = values.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {

        holder.itemView.setOnClickListener{
            clickListener(values[position])
        }
        super.onBindViewHolder(holder, position, payloads)
    }

    inner class ViewHolder(binding: FragmentComicWallItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val titleView: TextView = binding.itemTitle
        val contentView: TextView = binding.itemInfo
        val imageView: ImageView = binding.imageView

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

}