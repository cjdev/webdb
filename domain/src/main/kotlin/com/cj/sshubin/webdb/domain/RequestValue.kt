package com.cj.sshubin.webdb.domain

data class RequestValue(val method: String, val path: String, val query: String, val body: String)