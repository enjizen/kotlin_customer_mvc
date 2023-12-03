package com.wanchalerm.tua.customer.interceptor

import jakarta.servlet.ReadListener
import jakarta.servlet.ServletInputStream
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStream

class SimpleServletInputStream(data: ByteArray?) : ServletInputStream() {
    private val delegate: InputStream

    init {
        delegate = ByteArrayInputStream(data)
    }

    override fun isFinished(): Boolean = false

    override fun isReady(): Boolean = true

    override fun setReadListener(listener: ReadListener) {
        throw UnsupportedOperationException()
    }

    @Throws(IOException::class)
    override fun read(): Int = delegate.read()
}