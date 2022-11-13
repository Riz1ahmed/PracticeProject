package com.example.testproject.utilsAndData.model.figmaModel.document.figmaPage

import com.example.testproject.utilsAndData.model.figmaModel.document.figmaPage.children.Children
import com.example.testproject.utilsAndData.model.figmaModel.document.figmaPage.children.ColorRGBA

//Page/Root. Type: CANVAS
data class FigmaPage(
    val id: String,
    val name: String,
    val type: String,
    val backgroundColor: ColorRGBA,
    val children: List<Children>,
    val flowStartingPoints: List<Any>,
    val prototypeDevice: PrototypeDevice,
    val prototypeStartNodeID: Any,
    val scrollBehavior: String,
)