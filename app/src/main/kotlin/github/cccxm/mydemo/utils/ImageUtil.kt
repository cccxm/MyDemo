package github.cccxm.mydemo.utils

import android.support.annotation.DrawableRes
import android.widget.ImageView
import com.squareup.picasso.Picasso
import github.cccxm.mydemo.utils.transformation.CircleImageTransformation

/**
 * Created by cxm
 * on 2017/5/14.
 */

fun ImageView.inflateCircle(@DrawableRes id: Int) {
    Picasso.with(context).load(id).transform(CircleImageTransformation).into(this)
}

fun ImageView.inflate(@DrawableRes id: Int) {
    Picasso.with(context).load(id).into(this)
}