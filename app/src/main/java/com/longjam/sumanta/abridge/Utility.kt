package com.longjam.sumanta.abridge

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("avatarUrl")
fun setAvatarUrl(imageView: ImageView, avatarUrl: String?) {
    Glide.with(imageView.context)
        .load(avatarUrl)
        .into(imageView)
}

fun isOnline(context: Context): Boolean {
    val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo: NetworkInfo? = connMgr.activeNetworkInfo
    return networkInfo?.isConnected == true
}