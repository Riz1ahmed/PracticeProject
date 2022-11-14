package com.example.testproject.utilsAndData.model.figmaModel

import com.example.testproject.utilsAndData.model.figmaModel.document.Document

data class FigmaJson(
    //val componentSets: ComponentSets,
    //val components: Components,
    val document: Document,
    val editorType: String,
    val lastModified: String,
    val linkAccess: String,
    val name: String,
    val role: String,
    val schemaVersion: Int,
    val styles: Styles,
    val thumbnailUrl: String,
    val version: String
) {
    fun get1stPage() = document.figmaPages[0]
}