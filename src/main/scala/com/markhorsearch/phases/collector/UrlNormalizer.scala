package com.markhorsearch.phases.collector

import com.markhorsearch.phases.core.Site

object UrlNormalizer {

  
  def apply(url: String, provider: String): String = completeURL(url, provider)
  
  def completeURL(url: String, provider: String): String ={
    	if(url.startsWith("http")) url 
    	else if(url.startsWith("//")) "http:" + url
    	else if(url.startsWith("#")) ""
    		else if(url.startsWith("/")) provider + url
    		else url
    
  }
}