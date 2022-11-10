package com.example.testproject.utilsAndData.model.figmaModel

import com.example.testproject.utilsAndData.model.figmaModel.components.Components
import com.example.testproject.utilsAndData.model.figmaModel.componentsSets.ComponentSets
import com.example.testproject.utilsAndData.model.figmaModel.doument.Document

data class FigmaJson(
    val componentSets: ComponentSets,
    val components: Components,
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
)