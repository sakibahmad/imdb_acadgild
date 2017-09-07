package async;

/**
 * Created by root on 29/8/17.
 */


        import android.content.Context;
        import android.os.AsyncTask;
        import android.util.Log;
        import android.widget.Toast;


        import net.ServiceHandler;

        import org.json.JSONException;
        import org.json.JSONObject;

        import model.Constants;
        import model.MovieInfo;
//creating class for movies detail
public class MovieDetails extends AsyncTask<String, Void, MovieInfo> {

    private Context context;

    public MovieDetails(Context context) {

        this.context = context;
    }

    @Override
    //showing the movie detail with the help of service
    protected MovieInfo doInBackground(String... urls) {
        ServiceHandler sh = new ServiceHandler();
        String jsonStr = sh.makeServiceCall(urls[0], ServiceHandler.GET);

        if (jsonStr != null) {
            try {
                //creating the jason object
                JSONObject o = new JSONObject(jsonStr);
                //providint value

                MovieInfo movieInfo = new MovieInfo();
                movieInfo.setTitle(o.getString(Constants.TAG_TITLE));
                movieInfo.setDate(o.getString(Constants.TAG_RELEASE_DATE));
                movieInfo.setPoster(o.getString(Constants.TAG_POSTER_PATH));
                movieInfo.setVote_average(o.getString(Constants.TAG_VOTE_AVERAGE));
                movieInfo.setVote_count(o.getString(Constants.TAG_VOTE_COUNT));
                movieInfo.setBudget(o.getString(Constants.TAG_BUDGET));
                movieInfo.setRevenue(o.getString(Constants.TAG_REVENUE));
                movieInfo.setTagline(o.getString(Constants.TAG_TAGLINE));
                movieInfo.setStatus(o.getString(Constants.TAG_STATUS));
                movieInfo.setOverview(o.getString(Constants.TAG_OVERVIEW));

                return movieInfo;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("ServiceHandler", "Couldn't get any data from the url");
        }
        return null;
    }

    @Override
    protected void onPostExecute(MovieInfo result) {
        super.onPostExecute(result);
        //in case no value is returned

        if (result == null) {

            Toast.makeText(context, "Unable to fetch data from server", Toast.LENGTH_LONG).show();
        }
    }
}