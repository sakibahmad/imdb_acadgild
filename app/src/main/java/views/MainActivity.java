package views;

/**
 * Created by root on 29/8/17.
 */


        import android.os.Bundle;
        import android.support.v4.app.FragmentManager;
        import android.support.v4.app.FragmentTransaction;
        import android.support.v7.app.AppCompatActivity;

        import com.example.root.imdb.R;

        import fragment.MovieList;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        MovieList movieListFragment = new MovieList();
        transaction.replace(R.id.fragmentContainer, movieListFragment);
        transaction.commit();
    }
}