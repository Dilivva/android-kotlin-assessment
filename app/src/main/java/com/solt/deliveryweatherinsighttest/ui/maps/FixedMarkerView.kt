import android.graphics.Point
import android.graphics.PointF
import android.view.View
import org.maplibre.android.geometry.LatLng
import org.maplibre.android.maps.MapLibreMap
import org.maplibre.android.plugins.markerview.MarkerView
import org.maplibre.android.plugins.markerview.MarkerView.OnPositionUpdateListener

class FixedMarkerView( val latLng: LatLng,val view: View,val map: MapLibreMap) : MarkerView(latLng,view),OnPositionUpdateListener {
    init {
        setOnPositionUpdateListener(this)
    }
    override fun onUpdate(pointF: PointF?): PointF {
        //We want that the marker view will be in the middle of the point
        val lengthCenter = view.height/2
        val widthCenter = view.width/2
        if (pointF!=null){
            val pointUpdated = PointF(pointF.x-widthCenter,pointF.y-lengthCenter)
            return pointUpdated
        }
        else {
            //If there is no point f get the current point f using the projection
           return map.projection.toScreenLocation(latLng)
        }

    }

}


