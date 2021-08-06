package com.ruani.authdagger

interface OnSignCompleteListener {
    fun onSignComplete(email: String, password: String)
}