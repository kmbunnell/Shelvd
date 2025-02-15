package com.shelvd.data.model

import android.content.Context
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning

interface IsbnScanner {
    val scanner: GmsBarcodeScanner
}

class IsbnScannerImpl(appContext: Context) : IsbnScanner {
    private val options = GmsBarcodeScannerOptions.Builder()
        .setBarcodeFormats(Barcode.FORMAT_EAN_13)
        .enableAutoZoom().build()

    override val scanner = GmsBarcodeScanning.getClient(appContext, options)
}
