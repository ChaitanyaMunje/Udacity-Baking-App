package com.chinmay.chaitanyabakingapp.Widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.chinmay.chaitanyabakingapp.Database;
import com.chinmay.chaitanyabakingapp.Model.Ingredient;
import com.chinmay.chaitanyabakingapp.R;

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return (new ListProvider(this.getApplicationContext(), intent));
    }

    static class ListProvider implements RemoteViewsFactory {
        ArrayList<Ingredient> ingredients;
        final Context mContext;
        final int appWidgetId;

        ListProvider(Context context, Intent intent) {
            this.mContext = context;
            appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, 1);
        }


        @Override
        public void onCreate() {
        }

        @Override
        public void onDataSetChanged() {
            ingredients = new ArrayList();

            Database database = new Database(mContext);
            ingredients = database.getIngredients(appWidgetId);

        }

        @Override
        public void onDestroy() {

        }


        @Override
        public int getCount() {
            return ingredients.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {

            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_item);

            views.setTextViewText(R.id.widget_recipe_name, ingredients.get(position).getIngredient());
            views.setTextViewText(R.id.widget_recipe_measure, ingredients.get(position).getQuantity()
                    + " " + ingredients.get(position).getMeasure());


            return views;
        }


        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}
