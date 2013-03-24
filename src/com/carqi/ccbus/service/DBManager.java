package com.carqi.ccbus.service;
 
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream; 

import com.carqi.ccbus.activity.R;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;
 
public class DBManager {
	private static final String TAG = "DBManager";
    private final int BUFFER_SIZE = 400000;
    public static final String DB_NAME = "changchun.db";
    public static final String PACKAGE_NAME = "com.carqi.ccbus.activity";
    public static final String DB_PATH = "/data"
            + Environment.getDataDirectory().getAbsolutePath() + "/"+ PACKAGE_NAME;
    private SQLiteDatabase database;
    private Context context;
    private File file=null;
    
    DBManager(Context context) {
    	Log.i(TAG, "DBManger");
        this.context = context;
    }
 
    public void openDatabase() {
    	Log.i(TAG, "openDatabase()");
        String temp = DB_PATH + "/" + DB_NAME;
        Log.i(TAG, temp);
        this.database = this.openDatabase(DB_PATH + "/" + DB_NAME);
    }
    
    public SQLiteDatabase getDatabase(){
    	Log.i(TAG, "getDatabase()");
    	return this.database;
    }
 
    private SQLiteDatabase openDatabase(String dbfile) {
        try {
        	Log.i(TAG, "open and return");
        	file = new File(dbfile);
            if (!file.exists()) {
            	Log.i(TAG, "file");
            	InputStream inStream = context.getResources().openRawResource(R.raw.changchun);
            	if(inStream!=null){
            		Log.i(TAG, "inStream null");
            	}else{
            	}
            	FileOutputStream fos = new FileOutputStream(dbfile);
            	if(fos!=null){
            		Log.i(TAG, "fosnull");
            	}
                byte[] buffer = new byte[BUFFER_SIZE];
                int count = 0;
                while ((count =inStream.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                		Log.e(TAG, "while");
                	fos.flush();
                }
                fos.close();
                inStream.close();
            }
            database = SQLiteDatabase.openOrCreateDatabase(dbfile,null);
            return database;
        } catch (FileNotFoundException e) {
            Log.e(TAG, "File not found");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(TAG, "IO exception");
            e.printStackTrace();
        } catch (Exception e){
        	Log.e(TAG, "exception "+e.toString());
        }
        return null;
    }
    public void closeDatabase() {
    	Log.e("cc", "closeDatabase()");
    	if(this.database!=null)
    		this.database.close();
    }
}