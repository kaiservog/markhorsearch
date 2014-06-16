package com.markhorsearch.phases.cleaner.types

import com.markhorsearch.phases.core.document.Document

class WikiCleaner extends Cleaner {

  override def cleanSite(document: Document): Document = {
    val paragraphers = document.content \\ "p"
    val neo = document.newDocument(paragraphers.toList, true)
    neo.favicon = favicon(document)
    neo
  }
  
  
}