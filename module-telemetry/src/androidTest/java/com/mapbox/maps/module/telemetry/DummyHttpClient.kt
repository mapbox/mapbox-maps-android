package com.mapbox.maps.module.telemetry

import com.mapbox.bindgen.ExpectedFactory
import com.mapbox.common.*
import java.lang.RuntimeException
import java.util.concurrent.atomic.AtomicLong

class DummyHttpClient : HttpServiceInterface {
  private val requestId = AtomicLong(0)

  override fun setMaxRequestsPerHost(max: Byte) {}

  override fun setInterceptor(interceptor: HttpServiceInterceptorInterface?) {}

  override fun download(options: DownloadOptions, callback: DownloadStatusCallback): Long {
    throw RuntimeException("no impl")
  }

  override fun upload(options: UploadOptions, callback: UploadStatusCallback): Long {
    TODO("Not yet implemented")
  }

  override fun cancelUpload(id: Long, callback: ResultCallback) {
    TODO("Not yet implemented")
  }

  override fun cancelRequest(id: Long, callback: ResultCallback) {}

  override fun request(request: HttpRequest, callback: HttpResponseCallback): Long {
    val headers = HashMap<String, String>()
    var data = ByteArray(0)
    val resultData = HttpResponseData(headers, 200, data)
    val response = HttpResponse(0L, request, ExpectedFactory.createValue(resultData))
    callback.run(response)
    return requestId.incrementAndGet()
  }

  override fun supportsKeepCompression(): Boolean {
    return false
  }
}