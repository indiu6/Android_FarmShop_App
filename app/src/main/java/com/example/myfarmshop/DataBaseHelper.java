/*package com.example.myfarmshop;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;
    public static String DB_NAME = "Farm.db";   // Name of our database
    private SQLiteDatabase database;
    private static String TABLE_FARMS = "CREATE TABLE Farm(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "name TEXT," +
            "addres TEXT," +
            "phone TEXT," +
            "web TEXT," +
            "city TEXT," +
            "province TEXT," +
            "products TEXT)";

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        database = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_FARMS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertFarms(String name, String addres, String phone, String web, String city, String province, String products) {
        ContentValues cu = new ContentValues();
        cu.put("name", name);
        cu.put("addres", addres);
        cu.put("phone", phone);
        cu.put("web", web);
        cu.put("city", city);
        cu.put("province", province);
        cu.put("products", products);

        database.insert("Farm", null, cu);
    }

    public ArrayList<ModelFarms> getFarms() {
        ArrayList dt = new ArrayList<ModelFarms>();
        Cursor cu = database.rawQuery("SELECT _id, name, addres, phone, web, city, province, products FROM Farm", null);

        if (cu != null && cu.getCount() > 0) {
            cu.moveToFirst();
            do {
                ModelFarms model = new ModelFarms();

                model.set_id(cu.getInt(cu.getColumnIndex("_id")));
                model.setName(cu.getString(cu.getColumnIndex("name")));
                model.setAddres(cu.getString(cu.getColumnIndex("addres")));
                model.setPhone(cu.getString(cu.getColumnIndex("phone")));
                model.setWeb(cu.getString(cu.getColumnIndex("web")));
                model.setCity(cu.getString(cu.getColumnIndex("city")));
                model.setProvince(cu.getString(cu.getColumnIndex("province")));
                model.setProducts(cu.getString(cu.getColumnIndex("products")));

                dt.add(model);
            } while (cu.moveToNext());
        }

        return dt;
    }

    public void InsertDefault() {
        ArrayList data = this.getFarms();
        if (data.size() == 0) {
            insertFarms("Orchard Home Farm", "235 Howell Rd, Saint George, ON N0E 1N0", "(519) 448-1111",
                    "https://www.orchardhomefarm.com/", "Kitchener", "Ontario", "apples");
            insertFarms("Steckle Heritage Farm", "811 Bleams Rd, Kitchener, ON N2E 3X4", "(519) 748-4690",
                    "https://stecklehomestead.ca/", "Kitchener", "Ontario", " fruit various");
            insertFarms("The Tilts Farm", "Tilt Dr, Kitchener, ON N2P 1A6", "",
                    "http://www.tiltbuiltpastures.ca/", "Kitchener", "Ontario", "Sausages and ribs");
            insertFarms("Growing Hope FARM", "246 Sherring St, Cambridge, ON N3H 2W4", "",
                    "https://www.growinghopefarm.ca/", "Cambridge", "Ontario", "Chicken, Pork, Beef");
            insertFarms("", "", "",
                    "", "", "Ontario", "");
            insertFarms("", "", "",
                    "", "", "Ontario", "");
            insertFarms("", "", "",
                    "", "", "Ontario", "");

        }
    }

    public ArrayList<ModelFarms> getFarmsbyName(String name) {
        ArrayList dt = new ArrayList<ModelFarms>();
        Cursor cu = database.rawQuery("SELECT _id, name, addres, phone, web, city, province, products FROM Farm WHERE name like '%" + name + "%'", null);

        if (cu != null && cu.getCount() > 0) {
            cu.moveToFirst();
            do {
                ModelFarms model = new ModelFarms();

                model.set_id(cu.getInt(cu.getColumnIndex("_id")));
                model.setName(cu.getString(cu.getColumnIndex("name")));
                model.setAddres(cu.getString(cu.getColumnIndex("addres")));
                model.setPhone(cu.getString(cu.getColumnIndex("phone")));
                model.setWeb(cu.getString(cu.getColumnIndex("web")));
                model.setCity(cu.getString(cu.getColumnIndex("city")));
                model.setProvince(cu.getString(cu.getColumnIndex("province")));
                model.setProducts(cu.getString(cu.getColumnIndex("products")));

                dt.add(model);
            } while (cu.moveToNext());
        }

        return dt;
    }

    public ArrayList<ModelFarms> getFarmsbyProduct(String product) {
        ArrayList dt = new ArrayList<ModelFarms>();
        Cursor cu = database.rawQuery("SELECT _id, name, addres, phone, web, city, province, products FROM Farm WHERE products like '%" + product + "%'", null);

        if (cu != null && cu.getCount() > 0) {
            cu.moveToFirst();
            do {
                ModelFarms model = new ModelFarms();

                model.set_id(cu.getInt(cu.getColumnIndex("_id")));
                model.setName(cu.getString(cu.getColumnIndex("name")));
                model.setAddres(cu.getString(cu.getColumnIndex("addres")));
                model.setPhone(cu.getString(cu.getColumnIndex("phone")));
                model.setWeb(cu.getString(cu.getColumnIndex("web")));
                model.setCity(cu.getString(cu.getColumnIndex("city")));
                model.setProvince(cu.getString(cu.getColumnIndex("province")));
                model.setProducts(cu.getString(cu.getColumnIndex("products")));

                dt.add(model);
            } while (cu.moveToNext());
        }

        return dt;
    }
}

*/

/**********************************************   CODE IN V0.0.3   ********************************************/


package com.example.myfarmshop;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final int version = 1;
    public static String dbName = "Farm.db";   // Name of our database
    public static final String TABLE_NAME_FARM = "Farm";
    public static final String TABLE_NAME_PRODUCT = "Product";
    public static final String TABLE_NAME_OFFERING = "Offering";
    public static final String TABLE_NAME_SPECIAL_OFFER = "SpecialOffer";



    public DataBaseHelper(Context context) {
        super(context, dbName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }


    // Query to retrieve all products from database, without specific search condition
    public Cursor searchProduct(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        cursor = db.rawQuery("select * from " + TABLE_NAME_PRODUCT, null);

        if(cursor != null ){
            cursor.moveToFirst();
        }
        return cursor;
    }

    // Query to retrieve all special offers from database, without specific search condition
    public Cursor searchSpecialOffer(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        cursor = db.rawQuery("select * from " + TABLE_NAME_SPECIAL_OFFER, null);

        if(cursor != null ){
            cursor.moveToFirst();
        }
        return cursor;
    }

    // Query to retrieve all farms from database, without specific search condition
    public Cursor searchFarm(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        cursor = db.rawQuery("select * from " + TABLE_NAME_FARM, null);

        if(cursor != null ){
            cursor.moveToFirst();
        }
        return cursor;
    }

    // Query to retrieve a specific Farm from database, given its id
    public Cursor searchFarm(int farmId){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        cursor = db.rawQuery("select * from " + TABLE_NAME_FARM + " where id = " + farmId, null);

        if(cursor != null ){
            cursor.moveToFirst();
        }
        return cursor;
    }

    //View Data which looks for a table depending on the provided key and with a specific value
    public Cursor viewData(String key, String value){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;

        if(key == "farm"){
            cursor = db.rawQuery("select * from " + TABLE_NAME_FARM +
                                    " where name like '%" + value + "%'", null);
        }else if(key == "product")
        //Select SQL
        cursor = db.rawQuery("select f.* from " + TABLE_NAME_FARM + " f INNER JOIN " +
                                    TABLE_NAME_OFFERING + " o ON f.id=o.farmId INNER JOIN " +
                                    TABLE_NAME_PRODUCT + " p ON p.id=o.productId where p.name" +
                                    " = '" + value +"'", null);
//        cursor = db.rawQuery("select * from " + TABLE_NAME_PRODUCT + " where "
//                + key + " = '" + value +"'", null);
        if(cursor != null ){
            cursor.moveToFirst();
        }
        return cursor;
    }
}
