package com.sm678.taipeizoo.network

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import okhttp3.OkHttpClient
import java.io.InputStream


@GlideModule
class CustomGlideModule : AppGlideModule() {
    override fun isManifestParsingEnabled(): Boolean {
        return false
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        val okhttp = OkHttpClient.Builder()
            .sslSocketFactory(HttpsHelper.create().getSSLSocketFactory(), HttpsHelper.getTrustManager())
            .build()
        val factory = OkHttpUrlLoader.Factory(okhttp)
        glide.registry.replace(GlideUrl::class.java, InputStream::class.java, factory)
    }
}