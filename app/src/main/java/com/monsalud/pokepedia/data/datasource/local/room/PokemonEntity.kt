package com.monsalud.pokepedia.data.datasource.local.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_table")
class PokemonEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int = 0,
    @ColumnInfo(name = "name")
    var name: String = "",
    @ColumnInfo(name = "weight")
    var weight: Int = 0,
    @ColumnInfo(name = "height")
    var height: Int = 0,
    @ColumnInfo(name = "type")
    var type: String = "",
    @ColumnInfo(name = "image")
    var image: String = "",
    @ColumnInfo(name = "stats")
    var stats: String = ""
)

data class PokemonEntityList(
    var pokemonEntityList: List<PokemonEntity>
)
