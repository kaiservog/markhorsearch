package com.markhorsearch.phases.core

class Site(val url: String, val provider: String, val title: Option[String]) {
  
 override def toString = provider
 
}