package hr.domagoj.food;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {


    MainFragment mainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.main_fragment);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_add) {
            onMealAddAction();
            return true;
        } else if (item.getItemId() == R.id.menu_logs) {
            onDailyLogsAction();
            return true;
        } else if (item.getItemId() == R.id.menu_limit) {
            onChangeLimitAction();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onDailyLogsAction() {
        Intent intent = new Intent(this, LogsActivity.class);
        startActivity(intent);
    }

    public void onMealAddAction() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.dodaj_obrok));

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText titleBox = new EditText(this);
        titleBox.setHint(R.string.ime_hint);
        layout.addView(titleBox);

        final EditText descriptionBox = new EditText(this);
        descriptionBox.setHint(R.string.cal_hint);
        descriptionBox.setInputType(InputType.TYPE_CLASS_NUMBER);
        layout.addView(descriptionBox);

        builder.setView(layout);

        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String ime = titleBox.getText().toString();
                String cal = descriptionBox.getText().toString();

                Meal meal = new Meal(ime, Integer.parseInt(cal));
                Utils.addMeal(meal, MainActivity.this);
                mainFragment.loadData();
            }
        });
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    public void onChangeLimitAction() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.promjeni_dnevni_limit));

        int dailyLimit = Utils.getDailyLimit(this);


        final EditText input = new EditText(this);

        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setText(String.valueOf(dailyLimit));
        builder.setView(input);


        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String text = input.getText().toString();
                Utils.setDailyLimit(MainActivity.this, Integer.parseInt(text));
                if (input.getText().toString().trim().equals("")) {
                    input.requestFocus();
                } else {
                    dialog.dismiss();
                }
            }
        });
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
