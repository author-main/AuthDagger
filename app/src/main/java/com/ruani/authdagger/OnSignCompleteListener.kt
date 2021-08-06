package com.ruani.authdagger

interface OnSignCompleteListener {
    fun onSign(email: String, password: String)
}