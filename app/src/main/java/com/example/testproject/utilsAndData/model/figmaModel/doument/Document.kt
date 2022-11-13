package com.example.testproject.utilsAndData.model.figmaModel.doument

import com.example.testproject.utilsAndData.model.figmaModel.doument.children.FigmaPage

data class Document(
    val children: List<FigmaPage>,
    val id: String,
    val name: String,
    val scrollBehavior: String,
    val type: String
)