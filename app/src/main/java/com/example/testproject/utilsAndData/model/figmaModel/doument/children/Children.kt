package com.example.testproject.utilsAndData.model.figmaModel.doument.children

//Page/Root. Type: CANVAS
data class Children(
    val id: String,
    val name: String,
    val type: String,
    val backgroundColor: BackgroundColor,
    val children: List<ChildrenX>,

    val flowStartingPoints: List<Any>,
    val prototypeDevice: PrototypeDevice,
    val prototypeStartNodeID: Any,
    val scrollBehavior: String,
)