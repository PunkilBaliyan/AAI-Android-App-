import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.punkil.airportauthorityofindia.R


class SlideAdapter(private val context: Context) : RecyclerView.Adapter<SlideAdapter.SlideViewHolder>() {

    private val slideImages = arrayOf(R.drawable.aaiconnectjul, R.drawable.banner, R.drawable.bannerfraudulent,R.drawable.cyber,R.drawable.digiyatrabannerenglish,R.drawable.slide6)
    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlideViewHolder {
        val view = inflater.inflate(R.layout.slide_item, parent, false)
        return SlideViewHolder(view)
    }

    override fun onBindViewHolder(holder: SlideViewHolder, position: Int) {
        val imageRes = slideImages[position]
        holder.bind(imageRes)
    }

    override fun getItemCount(): Int = slideImages.size

    inner class SlideViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.slideImage)

        fun bind(imageRes: Int) {
            imageView.setImageResource(imageRes)
        }
    }
}

class SlidePagerTransformer : ViewPager2.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        page.apply {
            val scaleFactor = 0.85f
            val absPosition = Math.abs(position)
            scaleX = if (absPosition < 1) {
                scaleFactor + (1 - scaleFactor) * (1 - absPosition)
            } else {
                scaleFactor
            }
            scaleY = scaleFactor
        }
    }
}
