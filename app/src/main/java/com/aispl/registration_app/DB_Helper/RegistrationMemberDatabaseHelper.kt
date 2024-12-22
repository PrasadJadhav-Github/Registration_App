import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.aispl.registration_app.Model_Class.RegistrationMemberDatabase

class RegistrationMemberDatabaseHelper(context: Context) :
    SQLiteOpenHelper(
        context,
        RegistrationMemberDatabase.DATABASE_NAME,
        null,
        RegistrationMemberDatabase.DATABASE_VERSION
    ) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE ${RegistrationMemberDatabase.TABLE_NAME} (
                ${RegistrationMemberDatabase.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
                ${RegistrationMemberDatabase.COLUMN_MOBILE_NUMBER} TEXT,
                ${RegistrationMemberDatabase.COLUMN_NAME} TEXT,
                ${RegistrationMemberDatabase.COLUMN_ROLE} TEXT,
                ${RegistrationMemberDatabase.COLUMN_SUBSCRIPTION_FEE} REAL,
                ${RegistrationMemberDatabase.COLUMN_DEPOSIT_AMOUNT} REAL,
                ${RegistrationMemberDatabase.COLUMN_LOAN_AMOUNT} REAL,
                ${RegistrationMemberDatabase.COLUMN_GENDER} TEXT,
                ${RegistrationMemberDatabase.COLUMN_DOB} TEXT,
                ${RegistrationMemberDatabase.COLUMN_JOINING_DATE} TEXT,
                ${RegistrationMemberDatabase.COLUMN_MARITAL_STATUS} TEXT,
                ${RegistrationMemberDatabase.COLUMN_MARRIAGE_DATE} TEXT,
                ${RegistrationMemberDatabase.COLUMN_CASTE} TEXT,
                ${RegistrationMemberDatabase.COLUMN_RELIGION} TEXT,
                ${RegistrationMemberDatabase.COLUMN_CATEGORY} TEXT,
                ${RegistrationMemberDatabase.COLUMN_AADHAR_NUMBER} TEXT
            )
        """.trimIndent()
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${RegistrationMemberDatabase.TABLE_NAME}")
        onCreate(db)
    }

    fun insertRegistrationMemberData(
        mobileNumber: String,
        name: String,
        role: String,
        subscriptionFee: Double,
        depositAmount: Double,
        loanAmount: Double,
        gender: String,
        dob: String,
        joiningDate: String,
        maritalStatus: String,
        marriageDate: String?,
        caste: String,
        religion: String,
        category: String,
        aadharNumber: String
    ): Long {
        val db: SQLiteDatabase? = writableDatabase

        if (db == null) {
            throw IllegalStateException("Database not initialized")
        }

        val values = ContentValues().apply {
            put(RegistrationMemberDatabase.COLUMN_MOBILE_NUMBER, mobileNumber)
            put(RegistrationMemberDatabase.COLUMN_NAME, name)
            put(RegistrationMemberDatabase.COLUMN_ROLE, role)
            put(RegistrationMemberDatabase.COLUMN_SUBSCRIPTION_FEE, subscriptionFee)
            put(RegistrationMemberDatabase.COLUMN_DEPOSIT_AMOUNT, depositAmount)
            put(RegistrationMemberDatabase.COLUMN_LOAN_AMOUNT, loanAmount)
            put(RegistrationMemberDatabase.COLUMN_GENDER, gender)
            put(RegistrationMemberDatabase.COLUMN_DOB, dob)
            put(RegistrationMemberDatabase.COLUMN_JOINING_DATE, joiningDate)
            put(RegistrationMemberDatabase.COLUMN_MARITAL_STATUS, maritalStatus)
            put(RegistrationMemberDatabase.COLUMN_MARRIAGE_DATE, marriageDate)
            put(RegistrationMemberDatabase.COLUMN_CASTE, caste)
            put(RegistrationMemberDatabase.COLUMN_RELIGION, religion)
            put(RegistrationMemberDatabase.COLUMN_CATEGORY, category)
            put(RegistrationMemberDatabase.COLUMN_AADHAR_NUMBER, aadharNumber)
        }

        val result = db.insert(RegistrationMemberDatabase.TABLE_NAME, null, values)
        db.close()
        return result
    }

    fun getSpecificRegistrationMembers(): List<Map<String, String>> {
        val db = readableDatabase
        val memberList = mutableListOf<Map<String, String>>()

        val query = """
        SELECT 
            ${RegistrationMemberDatabase.COLUMN_NAME}, 
            ${RegistrationMemberDatabase.COLUMN_MOBILE_NUMBER}, 
            ${RegistrationMemberDatabase.COLUMN_ROLE}, 
            ${RegistrationMemberDatabase.COLUMN_SUBSCRIPTION_FEE}, 
            ${RegistrationMemberDatabase.COLUMN_DEPOSIT_AMOUNT}, 
            ${RegistrationMemberDatabase.COLUMN_LOAN_AMOUNT} 
        FROM ${RegistrationMemberDatabase.TABLE_NAME}
    """
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val member = mapOf(
                    RegistrationMemberDatabase.COLUMN_NAME to cursor.getString(cursor.getColumnIndexOrThrow(RegistrationMemberDatabase.COLUMN_NAME)),
                    RegistrationMemberDatabase.COLUMN_MOBILE_NUMBER to cursor.getString(cursor.getColumnIndexOrThrow(RegistrationMemberDatabase.COLUMN_MOBILE_NUMBER)),
                    RegistrationMemberDatabase.COLUMN_ROLE to cursor.getString(cursor.getColumnIndexOrThrow(RegistrationMemberDatabase.COLUMN_ROLE)),
                    RegistrationMemberDatabase.COLUMN_SUBSCRIPTION_FEE to cursor.getDouble(cursor.getColumnIndexOrThrow(RegistrationMemberDatabase.COLUMN_SUBSCRIPTION_FEE)).toString(),
                    RegistrationMemberDatabase.COLUMN_DEPOSIT_AMOUNT to cursor.getDouble(cursor.getColumnIndexOrThrow(RegistrationMemberDatabase.COLUMN_DEPOSIT_AMOUNT)).toString(),
                    RegistrationMemberDatabase.COLUMN_LOAN_AMOUNT to cursor.getDouble(cursor.getColumnIndexOrThrow(RegistrationMemberDatabase.COLUMN_LOAN_AMOUNT)).toString()
                )
                memberList.add(member)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()

        return memberList
    }
}
