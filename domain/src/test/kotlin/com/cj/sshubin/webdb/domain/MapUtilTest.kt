package com.cj.sshubin.webdb.domain

import com.cj.sshubin.webdb.domain.JsonUtil.mergeJsonStrings
import com.cj.sshubin.webdb.domain.JsonUtil.normalize
import kotlin.test.Test
import kotlin.test.assertEquals

class MapUtilTest {
  @Test
  fun testMerge() {
    val original = """{"a":1, "b":2, "c":3}"""
    val update = """{"b":3, "c":null, "d":4}"""
    val expected = """{"a":1, "b":3, "d":4}"""
    val actual = mergeJsonStrings(original, update)
    assertEquals(actual, normalize(expected))
  }

}
