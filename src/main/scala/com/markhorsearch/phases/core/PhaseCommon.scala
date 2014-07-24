package com.markhorsearch.phases.core

import com.markhorsearch.phases.core.document.Document

class PhaseCommon(val query: String) {
	var documents: List[Document] = List.empty[Document]
	var sites: List[Site] = List.empty[Site]
	var content: String = ""
}
