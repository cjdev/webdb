package com.cj.sshubin.webdb.domain

import org.junit.Assert.assertEquals
import org.junit.Test

class SplitPathTest {
  @Test
  fun empty() {
    check("", listOf())
  }

  @Test
  fun singleSlash() {
    check("/", listOf())
  }

  @Test
  fun a() {
    check("/aaa", listOf("aaa"))
  }

  @Test
  fun aSlash() {
    check("/aaa/", listOf("aaa"))
  }

  @Test
  fun b() {
    check("/aaa/bbb", listOf("aaa", "bbb"))
  }

  @Test
  fun bSlash() {
    check("/aaa/bbb/", listOf("aaa", "bbb"))
  }

  fun check(input: String, expected: List<String>) {
    val actual = Command.splitPath(input)
    assertEquals(expected, actual)
  }
}
