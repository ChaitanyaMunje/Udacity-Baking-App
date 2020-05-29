package com.chinmay.chaitanyabakingapp.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.chinmay.chaitanyabakingapp.Database;
import com.chinmay.chaitanyabakingapp.Interface.OnRequestFinishedListener;
import com.chinmay.chaitanyabakingapp.Model.Ingredient;
import com.chinmay.chaitanyabakingapp.Model.Recipe;
import com.chinmay.chaitanyabakingapp.NetworkOperations;
import com.chinmay.chaitanyabakingapp.R;
import com.chinmay.chaitanyabakingapp.Widget.RecipeWidget;
import com.chinmay.chaitanyabakingapp.Widget.WidgetModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Response;

public class RecipeWidgetConfigureActivity extends Activity implements OnRequestFinishedListener {
    private int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    private ProgressDialog progressDialog;
    private ArrayList<Recipe> recipeArrayList;
    private Spinner spinner;


    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            final Context context = RecipeWidgetConfigureActivity.this;

            int position = spinner.getSelectedItemPosition();
            WidgetModel model = new WidgetModel(recipeArrayList.get(position).getName(),
                    (ArrayList<Ingredient>) recipeArrayList.get(position).getIngredients());

            Database db = new Database(RecipeWidgetConfigureActivity.this);
            db.insertItem(model, mAppWidgetId);

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            RecipeWidget.updateAppWidget(context, appWidgetManager, mAppWidgetId);

            Intent resultValue = new Intent();
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
            setResult(RESULT_OK, resultValue);
            finish();
        }
    };

    public RecipeWidgetConfigureActivity() {
        super();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResult(RESULT_CANCELED);
        setContentView(R.layout.activity_recipe_widget_configure);


        spinner = findViewById(R.id.spinner);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();

        findViewById(R.id.add_button).setOnClickListener(mOnClickListener);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }

        NetworkOperations.getRecipes(this);


    }

    @Override
    public void onFailure() {
        progressDialog.dismiss();
        Toast.makeText(this, "There is a problem try again later ! or make sure you are connected to internet"
                , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(Response<List<Recipe>> response) {
        progressDialog.dismiss();
        recipeArrayList = (ArrayList<Recipe>) response.body();

        String[] values = new String[Objects.requireNonNull(recipeArrayList).size()];
        for (int i = 0; i < recipeArrayList.size(); i++) {
            values[i] = recipeArrayList.get(i).getName();
        }

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, values);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);
    }
}
