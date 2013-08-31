
package com.yoosufnabeel.mvscholarships;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
/**
 * Created by ynabyl on 8/29/13.
 */
public abstract class AbstractListViewActivity extends ListActivity {

    protected static final int PAGESIZE = 5;
    protected Datasource datasource;
    protected View footerView;
    protected int currentPage = 0;
    protected boolean loading = false;

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
       // Toast.makeText(this, getListAdapter().getItem(position) + " " + getString(R.string.selected), Toast.LENGTH_SHORT).show();
    }

    protected boolean load(int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        boolean lastItem = firstVisibleItem + visibleItemCount == totalItemCount && getListView().getChildAt(visibleItemCount - 1) != null && getListView().getChildAt(visibleItemCount - 1).getBottom() <= getListView().getHeight();
        boolean moreRows = getListAdapter().getCount() < datasource.getSize();
        return moreRows && lastItem && !loading;

    }

    protected class LoadNextPage extends AsyncTask<String, Void, String> {
        private ArrayList<Scholarship> newData = null;

        @Override
        protected String doInBackground(String... arg0) {

            try {
                currentPage++;

                URL url = new URL(
                        "http://whiterabbit.mv/ontime/home/index/" + currentPage);
                URLConnection connection = url.openConnection();


                String line;
                StringBuilder builder = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }

                ArrayList<Scholarship> lstScholarships = new ArrayList<Scholarship>();
                JSONArray pages = new JSONArray(builder.toString());

                for (int i = 0; i < pages.length(); ++i) {
                    JSONObject rec = pages.getJSONObject(i);
                    JSONObject jsonPage = rec.getJSONObject("Scholarship");
                    String title = Html.fromHtml(jsonPage.getString("Title")).toString();
                    //JSONObject jsonPage =jsonPage.getJSONObject("Links");

                    Scholarship schor = new Scholarship(R.drawable.scho_logo_new, title);
                    JSONArray links = jsonPage.getJSONArray("Links");

                    for (int j = 0; j < links.length(); ++j)
                    {
                        String documentName  = Html.fromHtml(links.getJSONObject(j).getString("Name")).toString();
                        String documentLink  = links.getJSONObject(j).getString("Link");

                        ScholarshipDocument newSchoDocument = new ScholarshipDocument(documentLink, documentName);
                        schor.documents.add(newSchoDocument);
                    }


                    datasource.addData(schor);

                }

            } catch (Exception e) {
                Log.e("AbstractListActivity", e.getMessage());
            }
            newData = datasource.getData(getListAdapter().getCount(), PAGESIZE);
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            ScholarshipAdapter customArrayAdapter = ((ScholarshipAdapter) getListAdapter());
            for (Scholarship value : newData) {
                customArrayAdapter.add(new Scholarship(R.drawable.scho_logo_new, value.name, value.documents));
            }
            customArrayAdapter.notifyDataSetChanged();

            getListView().removeFooterView(footerView);
            loading = false;
        }

    }
}