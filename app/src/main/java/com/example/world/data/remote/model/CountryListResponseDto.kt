package com.example.world.data.remote.model

import com.google.gson.annotations.SerializedName

data class CountryListResponseDto(

    @field:SerializedName("metadata")
	val metadata: Metadata? = null,

    @field:SerializedName("data")
	val data: List<DataItem?>? = null,

    @field:SerializedName("links")
	val links: List<LinksItem?>? = null
)

data class Metadata(

	@field:SerializedName("currentOffset")
	val currentOffset: Int? = null,

	@field:SerializedName("totalCount")
	val totalCount: Int? = null
)

data class LinksItem(

	@field:SerializedName("rel")
	val rel: String? = null,

	@field:SerializedName("href")
	val href: String? = null
)

data class DataItem(

	@field:SerializedName("wikiDataId")
	val wikiDataId: String? = null,

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("currencyCodes")
	val currencyCodes: List<String?>? = null,

	@field:SerializedName("name")
	val name: String? = null
)


