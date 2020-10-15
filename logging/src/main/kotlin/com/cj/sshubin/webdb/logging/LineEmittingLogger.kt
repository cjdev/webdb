package com.cj.sshubin.webdb.logging

class LineEmittingLogger(private val emit: (String) -> Unit) : Logger {
  override fun log(lines: List<String>) {
    lines.forEach(emit)
  }
}
