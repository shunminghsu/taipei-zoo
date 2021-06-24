package com.sm678.taipeizoo.network

import java.security.cert.X509Certificate
import javax.net.ssl.*

class HttpsHelper() {
    companion object Factory {
        const val CLIENT_AGREEMENT = "TLS"
        const val CLIENT_TRUST_MANAGER = "X509"

        fun create(): HttpsHelper = HttpsHelper()
        fun getTrustManager(): X509TrustManager = MyX509TrustManager()
    }

    private var sslSocketFactory: SSLSocketFactory
    private lateinit var mSslContext: SSLContext

    init {
        setTrustAll()
        sslSocketFactory = mSslContext.socketFactory
    }

    fun getSSLSocketFactory(): SSLSocketFactory {
        return sslSocketFactory
    }

    private fun setTrustAll() {
        try {
            mSslContext = SSLContext.getInstance(CLIENT_AGREEMENT)
            mSslContext.init(null, getTrustAllManager(), null)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getTrustAllManager(): Array<TrustManager> {
        return arrayOf<TrustManager>(MyX509TrustManager())
    }

    private class MyX509TrustManager : X509TrustManager {
        override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {

        }

        override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {

        }

        override fun getAcceptedIssuers(): Array<X509Certificate?> {
            return arrayOfNulls(0)
        }
    }

}