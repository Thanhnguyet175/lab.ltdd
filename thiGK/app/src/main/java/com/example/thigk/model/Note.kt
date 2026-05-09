package com.example.thigk.model

data class Note(
    var id: String? = null,
    var title: String? = null,
    var description: String? = null,
    var fileUrl: String? = null,
    var userId: String? = null
) {
    constructor() : this(null, null, null, null, null)
}