package com.example.testproject.utilsAndData.model.figmaModel

import com.example.testproject.utilsAndData.model.figmaModel.document.Document
import com.example.testproject.utilsAndData.model.figmaModel.document.figmaPage.children.Children

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
    fun get1stFrame(): Children {
        val page1st = document.figmaPages[0]
        return page1st.children[0]
    }
}