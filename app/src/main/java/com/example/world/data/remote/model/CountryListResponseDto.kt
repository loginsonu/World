package com.example.world.data.remote.model


data class CountryListResponseDto(
	val metadata: Metadata? = null,
	val data: List<DataItem?>? = null,
	val links: List<LinksItem?>? = null
)

data class Metadata(
	val currentOffset: Int? = null,
	val totalCount: Int? = null
)

data class LinksItem(
	val rel: String? = null,
	val href: String? = null
)

data class DataItem(
	val wikiDataId: String? = null,
	val code: String? = null,
	val currencyCodes: List<String?>? = null,
	val name: String? = null
)


