package com.recipebook.eatnow.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.NonNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.Executors;


@Database(entities = {Recipe.class},  version = 1, exportSchema = false)
public abstract class RecipeRoomDatabase extends RoomDatabase {

    //abstract method that returns the DAO class
    public abstract RecipeDAO recipeDAO();

    /*
        Make the RecipeRoomDatabase a singleton to prevent having multiple instances of
        the database
    */

    public static volatile RecipeRoomDatabase INSTANCE;

    public static Context mContext;

    static RecipeRoomDatabase getDatabase (Context context){
        mContext = context;
        final File dbFile = mContext.getDatabasePath("recipe_Database");

       if (INSTANCE == null){
            synchronized (RecipeRoomDatabase.class){
                if (INSTANCE == null){
                    //Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RecipeRoomDatabase.class, "recipe_Database").allowMainThreadQueries()
                            .addCallback(rRoomDatabaseCallBack).build();
                }
            }
        }
        return INSTANCE;
    }

    //compress images when new recipe with photo is added
    //insert as part of recipe
    private static RoomDatabase.Callback rRoomDatabaseCallBack = new RoomDatabase.Callback(){
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsync(INSTANCE).execute();
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    String[] fileNames = new String[]{"potato", "drink","redbean"};
                    try{
                        ContentResolver contentResolver =  mContext.getContentResolver();
                        for (String x : fileNames){
                            String fileName = x+".jpeg";
                            Uri fileUri = Uri.parse("android.resource://com.recipebook.eatnow/drawable/"+x);
                            Bitmap thumbnail = MediaStore.Images.Media.getBitmap(contentResolver,
                                    fileUri);
                            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                            thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                            FileOutputStream fileOutput = mContext.openFileOutput(fileName, 0);
                            fileOutput.write(bytes.toByteArray());
                            fileOutput.close();
                        }
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                    getDatabase(mContext).recipeDAO().insertAll(Recipe.dataSample());
                }
            });

        }

        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            //do i need this? -- for later
        }
    };

    /*
         Fill the database in the background
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final RecipeDAO rDao;

        PopulateDbAsync(RecipeRoomDatabase db) {
            rDao = db.recipeDAO();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time
            rDao.deleteAllRecipes();

            //Adding sample data
            Recipe r = new Recipe();

            return null;
        }
    }
}
