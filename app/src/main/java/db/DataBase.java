package db;

/**
 * Created by root on 29/8/17.
 */



        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;


        import java.util.ArrayList;
        import java.util.List;

        import model.MovieInfo;
//create class database for database activity
public class DataBase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Movie Database";
    private static final String TABLE_MOVIEDETAILS = "Movies";
    private static final String CREATE_TABLE = "CREATE TABLE ";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_RELEASE_DATE = "release_date";
    private static final String COLUMN_POSTER_PATH = "poster_path";
    private static final String COLUMN_VOTE_AVERAGE = "vote_average";
    private static final String COLUMN_VOTE_COUNT = "vote_count";
    private static final String COLUMN_IS_FAVORITE = "favorite";
    private static final String COLUMN_IS_WATCHLIST = "watchlist";
    private static final String DATATYPE_NUMERIC = " INTEGER";
    private static final String DATATYPE_VARCHAR = " TEXT";
    private static final String OPEN_BRACE = "(";
    private static final String COMMA = ",";

    //creating table
    private static final String CREATE_TABLE_MOVIEDETAILS = CREATE_TABLE + TABLE_MOVIEDETAILS + OPEN_BRACE +
            COLUMN_ID + DATATYPE_VARCHAR + COMMA +
            COLUMN_TITLE + DATATYPE_VARCHAR + COMMA +
            COLUMN_RELEASE_DATE + DATATYPE_VARCHAR + COMMA +
            COLUMN_POSTER_PATH + DATATYPE_VARCHAR + COMMA +
            COLUMN_VOTE_AVERAGE + DATATYPE_VARCHAR + COMMA +
            COLUMN_VOTE_COUNT + DATATYPE_VARCHAR + COMMA +
            COLUMN_IS_FAVORITE + DATATYPE_NUMERIC + COMMA +
            COLUMN_IS_WATCHLIST + DATATYPE_NUMERIC + COMMA +
            "UNIQUE(" + COLUMN_ID + ") ON CONFLICT REPLACE)";


    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_MOVIEDETAILS);

    }
    //adding movie information

    public void addMovie(MovieInfo movieInfo) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, movieInfo.getId());
        values.put(COLUMN_TITLE, movieInfo.getTitle());
        values.put(COLUMN_RELEASE_DATE, movieInfo.getDate());
        values.put(COLUMN_POSTER_PATH, movieInfo.getPoster());
        values.put(COLUMN_VOTE_AVERAGE, movieInfo.getVote_average());
        values.put(COLUMN_VOTE_COUNT, movieInfo.getVote_count());
        values.put(COLUMN_IS_FAVORITE, movieInfo.getFavorites());
        values.put(COLUMN_IS_WATCHLIST, movieInfo.getWatchList());
        db.insert(TABLE_MOVIEDETAILS, null, values);
        db.close();
    }


    public MovieInfo getMovie(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_MOVIEDETAILS, new String[]{COLUMN_TITLE, COLUMN_RELEASE_DATE,
                        COLUMN_POSTER_PATH, COLUMN_VOTE_AVERAGE, COLUMN_VOTE_COUNT, COLUMN_IS_FAVORITE, COLUMN_IS_WATCHLIST}, COLUMN_ID + "=?",
                new String[]{id}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        MovieInfo movieInfo = new MovieInfo();
        movieInfo.setId(id);
        if (cursor != null) {
            movieInfo.setTitle(cursor.getString(0));
            movieInfo.setDate(cursor.getString(1));
            movieInfo.setPoster(cursor.getString(2));
            movieInfo.setVote_average(cursor.getString(3));
            movieInfo.setVote_count(cursor.getString(4));
            movieInfo.setFavorites(cursor.getInt(5));
            movieInfo.setWatchList(cursor.getInt(6));
        }
        return movieInfo;
    }

    public boolean checkMovie(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_MOVIEDETAILS, null, COLUMN_ID + "=?", new String[]{id}, null, null, null, null);
        return cursor.getCount() > 0;
    }

    public int updateMovieF(MovieInfo movieInfo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_IS_FAVORITE, movieInfo.getFavorites());
        return db.update(TABLE_MOVIEDETAILS, values, COLUMN_ID + "=?", new String[]{movieInfo.getId()});
    }

    public int updateMovieW(MovieInfo movieInfo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_IS_WATCHLIST, movieInfo.getWatchList());
        return db.update(TABLE_MOVIEDETAILS, values, COLUMN_ID + "=?", new String[]{movieInfo.getId()});
    }

    public List<MovieInfo> getFavorites() {
        List<MovieInfo> allMovies = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_MOVIEDETAILS,
                new String[]{COLUMN_ID, COLUMN_TITLE, COLUMN_RELEASE_DATE, COLUMN_POSTER_PATH,
                        COLUMN_VOTE_AVERAGE, COLUMN_VOTE_COUNT, COLUMN_IS_FAVORITE}
                , COLUMN_IS_FAVORITE + "=?", new String[]{String.valueOf(1)}, null, null, null);

        //moving cursor
        if (cursor.moveToFirst()) {
            do {
                MovieInfo movieInfo = new MovieInfo();
                movieInfo.setId(cursor.getString(0));
                movieInfo.setTitle(cursor.getString(1));
                movieInfo.setDate(cursor.getString(2));
                movieInfo.setPoster(cursor.getString(3));
                movieInfo.setVote_average(cursor.getString(4));
                movieInfo.setVote_count(cursor.getString(5));
                movieInfo.setFavorites(cursor.getInt(6));
                if (cursor.getInt(6) == 1) {
                    allMovies.add(movieInfo);
                }
            } while (cursor.moveToNext());
        }
        return allMovies;
    }
    //list of movie watched

    public List<MovieInfo> getWatchList() {
        List<MovieInfo> allMovies = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_MOVIEDETAILS,
                new String[]{COLUMN_ID, COLUMN_TITLE, COLUMN_RELEASE_DATE, COLUMN_POSTER_PATH,
                        COLUMN_VOTE_AVERAGE, COLUMN_VOTE_COUNT, COLUMN_IS_WATCHLIST}
                , COLUMN_IS_WATCHLIST + "=?", new String[]{String.valueOf(1)}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                MovieInfo movieInfo = new MovieInfo();
                movieInfo.setId(cursor.getString(0));
                movieInfo.setTitle(cursor.getString(1));
                movieInfo.setDate(cursor.getString(2));
                movieInfo.setPoster(cursor.getString(3));
                movieInfo.setVote_average(cursor.getString(4));
                movieInfo.setVote_count(cursor.getString(5));
                movieInfo.setWatchList(cursor.getInt(6));
                if (cursor.getInt(6) == 1) {
                    allMovies.add(movieInfo);
                }
            } while (cursor.moveToNext());
        }
        return allMovies;
    }

    public void deleteNonFavWatchMovie() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MOVIEDETAILS, COLUMN_IS_FAVORITE + "=? AND " + COLUMN_IS_WATCHLIST + "=?", new String[]{String.valueOf(0), String.valueOf(0)});
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIEDETAILS);
        onCreate(db);
    }
}
