package com.example.testproject.utilsAndData.model.figmaModel.doument.children

//Page/Root. Type: CANVAS
data class FigmaPage(
    val id: String,
    val name: String,
    val type: String,
    val backgroundColor: BackgroundColor,
    val children: List<Children>,

    val flowStartingPoints: List<Any>,
    val prototypeDevice: PrototypeDevice,
    val prototypeStartNodeID: Any,
    val scrollBehavior: String,
)