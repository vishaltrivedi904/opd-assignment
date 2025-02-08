package com.example.khushibaby.utils

import android.graphics.pdf.PdfDocument
import android.os.Build
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.print.PrintManager
import android.content.Context
import android.os.Bundle
import android.os.CancellationSignal
import android.print.PageRange
import android.print.PrintDocumentInfo
import androidx.compose.ui.platform.LocalContext
import java.io.FileOutputStream

class SummaryPrintAdapter(private val context: Context, private val summaryContent: String) : PrintDocumentAdapter() {


    override fun onLayout(
        oldAttributes: PrintAttributes?,
        newAttributes: PrintAttributes?,
        cancellationSignal: CancellationSignal?,
        callback: LayoutResultCallback?,
        extras: Bundle?
    ) {
        callback?.onLayoutFinished(
            PrintDocumentInfo.Builder("summary_print.pdf")
                .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                .setPageCount(1)
                .build(),
            true
        )
    }

    override fun onWrite(
        pages: Array<out android.print.PageRange>?,
        destination: android.os.ParcelFileDescriptor?,
        cancellationSignal: android.os.CancellationSignal?,
        callback: WriteResultCallback?
    ) {
        val pdfDocument = PdfDocument()

        // Write content to the PDF document
        val page = PdfDocument.PageInfo.Builder(595, 842, 1).create()
        val pdfPage = pdfDocument.startPage(page)

        val canvas = pdfPage.canvas
        val paint = android.graphics.Paint()

        // Draw the summary content (you can modify this to fit the page better)
        canvas.drawText(summaryContent, 10f, 10f, paint)

        pdfDocument.finishPage(pdfPage)
        destination?.let {

            try {
                val fileOutputStream = FileOutputStream(it.fileDescriptor)
                pdfDocument.writeTo(fileOutputStream)
                fileOutputStream.close()
                callback?.onWriteFinished(arrayOf(PageRange.ALL_PAGES))
            } catch (e: Exception) {
                callback?.onWriteFailed(e.localizedMessage)
            }


        }


        pdfDocument.close()
    }
}
