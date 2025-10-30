package com.unclekostya.bookstore.data.local.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import androidx.room.withTransaction
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.unclekostya.bookstore.R
import com.unclekostya.bookstore.data.local.dao.StoreDao
import com.unclekostya.bookstore.data.local.entity.Cart
import com.unclekostya.bookstore.data.local.entity.Product
import com.unclekostya.bookstore.data.local.entity.ProductCharacteristic
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromIntList(list: List<Int>?): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toIntList(data: String?): List<Int> {
        if (data.isNullOrEmpty()) return emptyList()
        val type = object : TypeToken<List<Int>>() {}.type
        return gson.fromJson(data, type)
    }
}
@Database(
    entities = [Product::class, ProductCharacteristic::class, Cart::class],
    version = 1,
    exportSchema =  false
)
@TypeConverters(Converters::class)
abstract class ProductDatabase: RoomDatabase() {
    abstract fun productDao(): StoreDao

    companion object {
        @Volatile
        private var Instance: ProductDatabase? = null

        fun getDatabase(context: Context): ProductDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, ProductDatabase::class.java, "product_database")
                    .addCallback(PrepopulateRoomCallback(context))
                    .build()
                    .also { Instance = it }
            }
        }
    }
}


class PrepopulateRoomCallback(private val context: Context) : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        CoroutineScope(Dispatchers.IO).launch {
            val database = ProductDatabase.getDatabase(context)
            val dao = database.productDao()
            database.withTransaction {
                prePopulateProduct(context,dao)
            }
        }
    }
}

suspend fun prePopulateProduct(
    context: Context,
    dao: StoreDao
) {
    try {
        val root = context.resources.openRawResource(R.raw.products)
            .bufferedReader().use { JSONObject(it.readText()) }

        val productsArray = root.getJSONArray("products")
        val productCharacteristicArray = root.getJSONArray("productCharacteristics")

        for (pIndex in 0 until productsArray.length()) {
            val productObject = productsArray.getJSONObject(pIndex)

            val productDb = dao.insertProduct(
                Product(
                    productId = productObject.getInt("productId"),
                    productName = productObject.getString("productName"),
                    productCost = productObject.getString("productCost"),
                    count = productObject.getInt("count"),
                    productImageUrl = productObject.getString("productImageUrl")
                )
            )
        }

        for (pCharacteristicIndex in 0 until productCharacteristicArray.length()) {
            val productCharacteristicObject = productCharacteristicArray.getJSONObject(pCharacteristicIndex)

            val productCharacteristicDb = dao.insertProductCharacteristic(
                ProductCharacteristic(
                    productId = productCharacteristicObject.getInt("productCharacteristicId"),
                    phoneModel = productCharacteristicObject.getString("phoneModel"),
                    phoneOs = productCharacteristicObject.getString("phoneOs"),
                    phoneDisplay = productCharacteristicObject.getString("phoneDisplay"),
                    phoneRefreshRate = productCharacteristicObject.getInt("phoneRefreshRate"),
                    phoneProcessor = productCharacteristicObject.getString("phoneProcessor"),
                    phoneStorage = productCharacteristicObject.getInt("phoneStorage"),
                    phoneRam = productCharacteristicObject.getInt("phoneRam"),
                    phoneBatteryCapacity = productCharacteristicObject.getInt("phoneBatteryCapacity")
                )
            )
        }

    } catch (e: Exception) {
        Log.e("DB", "Error while reading Json: ${e.message}")
    }
}