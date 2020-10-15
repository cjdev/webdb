package com.cj.sshubin.webdb.json

import com.cj.sshubin.webdb.json.JsonUtil.mergeJsonStrings
import com.cj.sshubin.webdb.json.JsonUtil.normalizeJson
import org.junit.Test
import kotlin.test.assertEquals

class MapUtilTest {
  @Test
  fun testMerge() {
    val original = """{"a":1, "b":2, "c":3}"""
    val update = """{"b":3, "c":null, "d":4}"""
    val expected = """{"a":1, "b":3, "d":4}"""
    val actual = mergeJsonStrings(original, update)
    assertEquals(actual, expected.normalizeJson())
  }
}
