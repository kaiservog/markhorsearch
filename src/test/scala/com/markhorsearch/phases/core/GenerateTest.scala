package com.markhorsearch.phases.core

import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.specs2.runner.JUnitRunner


@RunWith(classOf[JUnitRunner])
class GenerateTest extends FlatSpec {
	"Generate" should "create phases" in {
	  val g = new Generate
	  println(g.generate)
	}
}