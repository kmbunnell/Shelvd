package com.shelvd.domain

import com.shelvd.data.model.ApiResult
import com.shelvd.data.model.BookResult
import com.shelvd.data.model.IsbnScanner
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

class ScanBookUseCase(
    val isbnScanner: IsbnScanner,
    val isbnLookUpUseCase: IsbnLookUpUseCase,
) {

    operator fun invoke(): Flow<ApiResult<BookResult>> {
        return callbackFlow {
            isbnScanner.scanner.startScan()
                .addOnSuccessListener { barcode ->
                    launch {
                        barcode.rawValue?.let {
                            isbnLookUpUseCase.invoke(it).collect { data ->
                                send(data)
                            }
                        }
                    }
                }
                .addOnFailureListener {
                    it.printStackTrace()
                }

            awaitClose { }
        }
    }
}



