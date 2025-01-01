package com.shelvd.domain

import android.content.Context
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

class ScanBookUseCase(
    appContext: Context,
) {
    private val options = GmsBarcodeScannerOptions.Builder()
        .setBarcodeFormats(Barcode.FORMAT_EAN_13)
        .enableAutoZoom().build()

    private val scanner = GmsBarcodeScanning.getClient(appContext, options)

    operator fun invoke(): Flow<String?> {
        return callbackFlow {
            scanner.startScan().addOnSuccessListener { barcode ->
                launch {
                   send(barcode.rawValue)
                }
            }
                .addOnFailureListener{
                    it.printStackTrace()
                }

            awaitClose { }
        }
    }


}


