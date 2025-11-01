package com.unclekostya.bookstore.data.local.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.withTransaction
import androidx.sqlite.db.SupportSQLiteDatabase
import com.unclekostya.bookstore.R
import com.unclekostya.bookstore.data.local.dao.StoreDao
import com.unclekostya.bookstore.data.local.entity.Cart
import com.unclekostya.bookstore.data.local.entity.Product
import com.unclekostya.bookstore.data.local.entity.ProductCharacteristic
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

@Database(
    entities = [Product::class, ProductCharacteristic::class, Cart::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun productDao(): StoreDao

    companion object {
        @Volatile
        private var Instance: ProductDatabase? = null

        fun getDatabase(context: Context): ProductDatabase {
            return Instance ?: synchronized(this) {
                val db = Room.databaseBuilder(context, ProductDatabase::class.java, "product_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(PrepopulateRoomCallback(context))
                    .build()

                CoroutineScope(Dispatchers.IO).launch {
                    val dao = db.productDao()
                    db.withTransaction {
                        prePopulateProduct(context, dao)
                    }
                    Log.d("DB", "✅ Forced prepopulate on startup")
                }

                db.also { Instance = it }
            }
        }
    }
}

class PrepopulateRoomCallback(private val context: Context) : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        Log.d("DB", "🔥 onCreate() called — prepopulating...")

        CoroutineScope(Dispatchers.IO).launch {
            val database = ProductDatabase.getDatabase(context)
            val dao = database.productDao()
            database.withTransaction {
                prePopulateProduct(context, dao)
            }
            Log.d("DB", "✅ Database prepopulated successfully")
        }
    }
}

suspend fun prePopulateProduct(context: Context, dao: StoreDao) {
    try {
        val root = context.resources.openRawResource(R.raw.products)
            .bufferedReader().use { JSONObject(it.readText()) }

        val productsArray = root.getJSONArray("products")
        val productCharacteristicArray = root.getJSONArray("productCharacteristics")

        Log.d("DB", "Found ${productsArray.length()} products")

        for (i in 0 until productsArray.length()) {
            val obj = productsArray.getJSONObject(i)
            dao.insertProduct(
                Product(
                    productId = obj.getInt("productId"),
                    productName = obj.getString("productName"),
                    productCost = obj.getString("productCost"),
                    count = obj.getInt("count"),
                    productImageUrl = obj.getString("productImageUrl")
                )
            )
            Log.d("DB", "Inserted: ${obj.getString("productName")}")
        }

        for (i in 0 until productCharacteristicArray.length()) {
            val c = productCharacteristicArray.getJSONObject(i)
            dao.insertProductCharacteristic(
                ProductCharacteristic(
                    productId = c.getInt("productCharacteristicId"),
                    phoneModel = c.optString("phoneModel", null),
                    phoneOs = c.optString("phoneOs", null),
                    phoneDisplay = c.optString("phoneDisplay", null),
                    phoneRefreshRate = c.optInt("phoneRefreshRate"),
                    phoneProcessor = c.optString("phoneProcessor", null),
                    phoneStorage = c.optInt("phoneStorage"),
                    phoneRam = c.optInt("phoneRam"),
                    phoneBatteryCapacity = c.optInt("phoneBatteryCapacity")
                )
            )
        }
    } catch (e: Exception) {
        Log.e("DB", "❌ Error while prepopulating: ${e.message}")
    }
}
