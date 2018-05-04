package hci.univie.ac.at.a3_yawa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartScreen extends AppCompatActivity {

    private Button button1;
    private Button button2;
    private Button button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        // Bind first city button to the city screen
        button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCityScreen("Vienna,AT");
            }
        });

        // Bind second city button to the city screen
        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCityScreen("London,UK");
            }
        });

        // Bind third city button to the city screen
        button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCityScreen("Istanbul,TR");
            }
        });
    }

    public void openCityScreen(String selectedCity) {
        Intent intent = new Intent(this, CityScreen.class);
        // Pass the selected city parameter
        intent.putExtra("selectedCity", selectedCity);
        startActivity(intent);
    }
}
