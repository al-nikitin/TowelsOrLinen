package com.appshop162.towelsorlinen;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    String towelsOrLinen, towels, linen;
    Button button;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.textview);
        towels = getString(R.string.towels);
        linen = getString(R.string.linen);

        initializeUI();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (!towelsOrLinen.equals("-")) {
                    handleButtonClick(view);
                } else {
                    PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
                    popupMenu.inflate(R.menu.popup);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            int id = menuItem.getItemId();
                            switch (id) {
                                case R.id.popup_item_linen:
                                    towelsOrLinen = towels;
                                    handleButtonClick(view);
                                    break;
                                case R.id.popup_item_towels:
                                    towelsOrLinen = linen;
                                    handleButtonClick(view);
                                    break;
                            }
                            return false;
                        }
                    });
                    popupMenu.show();
                }
            }
        });
    }

    public String getDate() {
        Date date = Calendar.getInstance().getTime();
        int day = date.getDate();
        int month = date.getMonth() + 1;
        String monthS = String.valueOf(month);
        if (monthS.length() == 1) monthS = "0" + monthS;
        int year = date.getYear() + 1900;
        String dateS = day + "." + monthS + "." + year;
        return dateS;
    }

    public void handleButtonClick(View view) {
        towelsOrLinen = (towelsOrLinen.equals(linen)) ? towels : linen;
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(view.getContext()).edit();
        editor.putString("towels_or_linen", towelsOrLinen);
        editor.putString("previous_date", getDate());
        editor.apply();
        initializeUI();
    }

    public void initializeUI() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

        towelsOrLinen = sp.getString("towels_or_linen", "-");
        String previousDate = sp.getString("previous_date", "-");

        String buttonText;
        if (towelsOrLinen.equals("-")) {
            buttonText = "";
        } else if (towelsOrLinen.equals(linen)) {
            buttonText = towels;
        } else buttonText = linen;

        button.setText(buttonText);

        String textviewText = "";
        if (!previousDate.equals("-")) textviewText = getString(R.string.textview_base_text1) +
                previousDate + getString(R.string.textview_base_text2) + towelsOrLinen;

        textView.setText(textviewText);
    }
}
