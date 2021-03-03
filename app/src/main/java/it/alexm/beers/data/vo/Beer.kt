package it.alexm.beers.data.vo

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class Beer(
    @SerializedName("abv")
    val abv: Double?,
    @SerializedName("attenuation_level")
    val attenuationLevel: Double?,
    @SerializedName("boil_volume")
    val boilVolume: BoilVolume?,
    @SerializedName("brewers_tips")
    val brewersTips: String?,
    @SerializedName("contributed_by")
    val contributedBy: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("ebc")
    val ebc: Double?,
    @SerializedName("first_brewed")
    val firstBrewed: String?,
    @SerializedName("food_pairing")
    val foodPairing: List<String>?,
    @SerializedName("ibu")
    val ibu: Double?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("ingredients")
    val ingredients: Ingredients?,
    @SerializedName("method")
    val method: Method?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("ph")
    val ph: Double?,
    @SerializedName("srm")
    val srm: Double?,
    @SerializedName("tagline")
    val tagline: String?,
    @SerializedName("target_fg")
    val targetFg: Double?,
    @SerializedName("target_og")
    val targetOg: Double?,
    @SerializedName("volume")
    val volume: Volume?
) : Parcelable {

    override fun hashCode(): Int = this.id ?: 0

    override fun equals(other: Any?): Boolean {
        return other is Beer && this.id == other.id
    }
}

@Parcelize
data class BoilVolume(
    @SerializedName("unit")
    val unit: String?,
    @SerializedName("value")
    val value: Int?
) : Parcelable

@Parcelize
data class Ingredients(
    @SerializedName("hops")
    val hops: List<Hop>?,
    @SerializedName("malt")
    val malt: List<Malt>?,
    @SerializedName("yeast")
    val yeast: String?
) : Parcelable

@Parcelize
data class Hop(
    @SerializedName("add")
    val add: String?,
    @SerializedName("amount")
    val amount: Amount?,
    @SerializedName("attribute")
    val attribute: String?,
    @SerializedName("name")
    val name: String?
) : Parcelable

@Parcelize
data class Method(
    @SerializedName("fermentation")
    val fermentation: Fermentation?,
    @SerializedName("mash_temp")
    val mashTemp: List<MashTemp>?,
    @SerializedName("twist")
    val twist: String?
) : Parcelable

@Parcelize
data class Volume(
    @SerializedName("unit")
    val unit: String?,
    @SerializedName("value")
    val value: Int?
) : Parcelable

@Parcelize
data class Malt(
    @SerializedName("amount")
    val amount: Amount?,
    @SerializedName("name")
    val name: String?
) : Parcelable

@Parcelize
data class Amount(
    @SerializedName("unit")
    val unit: String?,
    @SerializedName("value")
    val value: Double?
) : Parcelable

@Parcelize
data class Fermentation(
    @SerializedName("temp")
    val temp: Temp?
) : Parcelable

@Parcelize
data class MashTemp(
    @SerializedName("duration")
    val duration: Int?,
    @SerializedName("temp")
    val temp: Temp?
) : Parcelable

@Parcelize
data class Temp(
    @SerializedName("unit")
    val unit: String?,
    @SerializedName("value")
    val value: Double?
) : Parcelable