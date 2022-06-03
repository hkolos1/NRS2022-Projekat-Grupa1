package com.example.roomapp.model

import androidx.room.Embedded
import androidx.room.Relation

data class BranchWithProducts (
    @Embedded val branch: Branch,
    @Relation(
        parentColumn = "id",
        entityColumn = "branchId"
    )
    val products: List<Product>
)