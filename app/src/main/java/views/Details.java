package views;

/**
 * Created by root on 29/8/17.
 */

        import android.os.Bundle;
        import android.support.v4.app.FragmentManager;
        import android.support.v4.app.FragmentTransaction;
        import android.support.v7.app.AppCompatActivity;

        import com.example.root.imdb.R;

        import fragment.MovieDetails;


public class Details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        MovieDetails movieDetails = new MovieDetails();
        movieDetails.setArguments(getIntent().getExtras());
        transaction.replace(R.id.fragmentContainer2, movieDetails);
        transaction.commit();
    }
}