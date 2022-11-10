package com.example.testproject.utilsAndData.model.figmaModel.doument

import com.example.testproject.utilsAndData.model.figmaModel.doument.children.Children

data class Document(
    val children: List<Children>,
    val id: String,
    val name: String,
    val scrollBehavior: String,
    val type: String
)