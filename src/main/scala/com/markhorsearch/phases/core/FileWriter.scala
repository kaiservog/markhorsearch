package com.markhorsearch.phases.core

import java.io.File
import java.io.PrintWriter

class FileWriter(path: String, content: String) {
	def write() = {
	  val writer = new PrintWriter(new File(path+"\\test.html" ))

      writer.write(content)
      writer.close()
	}
}