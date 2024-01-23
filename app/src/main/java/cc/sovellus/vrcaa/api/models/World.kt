package cc.sovellus.vrcaa.api.models


import com.google.gson.annotations.SerializedName

data class World(
    @SerializedName("authorId")
    val authorId: String,
    @SerializedName("authorName")
    val authorName: String,
    @SerializedName("capacity")
    val capacity: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("favorites")
    val favorites: Int,
    @SerializedName("featured")
    val featured: Boolean,
    @SerializedName("heat")
    val heat: Int,
    @SerializedName("id")
    val id: String,
    @SerializedName("imageUrl")
    val imageUrl: String,
    @SerializedName("instances")
    val instances: List<List<Any>>,
    @SerializedName("labsPublicationDate")
    val labsPublicationDate: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("namespace")
    val namespace: String,
    @SerializedName("occupants")
    val occupants: Int,
    @SerializedName("organization")
    val organization: String,
    @SerializedName("popularity")
    val popularity: Int,
    @SerializedName("previewYoutubeId")
    val previewYoutubeId: String,
    @SerializedName("privateOccupants")
    val privateOccupants: Int,
    @SerializedName("publicOccupants")
    val publicOccupants: Int,
    @SerializedName("publicationDate")
    val publicationDate: String,
    @SerializedName("recommendedCapacity")
    val recommendedCapacity: Int,
    @SerializedName("releaseStatus")
    val releaseStatus: String,
    @SerializedName("tags")
    val tags: List<String>,
    @SerializedName("thumbnailImageUrl")
    val thumbnailImageUrl: String,
    @SerializedName("udonProducts")
    val udonProducts: List<String>,
    @SerializedName("unityPackages")
    val unityPackages: List<UnityPackage>,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("version")
    val version: Int,
    @SerializedName("visits")
    val visits: Int
) {
    data class UnityPackage(
        @SerializedName("assetUrl")
        val assetUrl: String,
        @SerializedName("assetUrlObject")
        val assetUrlObject: AssetUrlObject?,
        @SerializedName("assetVersion")
        val assetVersion: Int,
        @SerializedName("created_at")
        val createdAt: String,
        @SerializedName("id")
        val id: String,
        @SerializedName("platform")
        val platform: String,
        @SerializedName("pluginUrl")
        val pluginUrl: String,
        @SerializedName("pluginUrlObject")
        val pluginUrlObject: PluginUrlObject?,
        @SerializedName("unitySortNumber")
        val unitySortNumber: Long,
        @SerializedName("unityVersion")
        val unityVersion: String
    ) {
        class AssetUrlObject

        class PluginUrlObject
    }
}