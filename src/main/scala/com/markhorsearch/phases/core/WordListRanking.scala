package com.markhorsearch.phases.core

import scala.collection.mutable.Map


class WordListRanking {
	val words:Map[String, Int] = Map()
	
	def addWord(word: String, rank: Int) = {
	  val ranked = words.get(word).getOrElse(0)
	  words += (word -> (ranked + rank))
	}
	
	
}