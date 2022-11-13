package com.example.testproject.utilsAndData.model.figmaModel.document

import com.example.testproject.utilsAndData.model.figmaModel.document.figmaPage.FigmaPage
import com.google.gson.annotations.SerializedName

data class Document(
    @SerializedName("children")
    val figmaPages: List<FigmaPage>,
    val id: String,
    val name: String,
    val scrollBehavior: String,
    val type: String
)