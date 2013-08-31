package com.yoosufnabeel.mvscholarships;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ynabyl on 8/29/13.
 */
public class ScholarshipAdapter extends ArrayAdapter<Scholarship> {

    Context context;
    int layoutResourceId;
    ArrayList<Scholarship> data = null;


    public ScholarshipAdapter(Context context, int layoutResourceId, ArrayList<Scholarship> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ScholarshipHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ScholarshipHolder();
            holder.imgIcon = (ImageView) row.findViewById(R.id.schoLogo);
            holder.txtTitle = (TextView) row.findViewById(R.id.schoName);

            holder.tblLayout = (TableLayout) row.findViewById(R.id.tblDocuments);


            row.setTag(holder);
        } else {
            holder = (ScholarshipHolder) row.getTag();
        }

        Scholarship scholarship = data.get(position);
        holder.txtTitle.setText(scholarship.name);
        holder.imgIcon.setImageResource(R.drawable.scho_logo_new);
        holder.tblLayout.removeAllViews();
        for (int i = 0; i < data.get(position).documents.size(); ++i) {
            TableRow tr = new TableRow(getContext());

            TextView messageTV = new TextView(getContext());
            messageTV.setText(scholarship.documents.get(i).name);
            messageTV.setTextSize(18);
            messageTV.setClickable(true);
            messageTV.setPadding(0, 3, 0, 3);

            messageTV.setTag(scholarship.documents.get(i).url);

            //set colors
            ColorStateList cl = null;
            try {
                XmlResourceParser xpp = getContext().getResources().getXml(R.xml.document_selector_color);
                cl = ColorStateList.createFromXml( getContext().getResources(), xpp);
            } catch (Exception e) {}
            messageTV.setTextColor(cl);

            tr.addView(messageTV); // sets message id to the row.
            messageTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // Toast.makeText(getContext(), v.getTag().toString(), Toast.LENGTH_SHORT).show();


                    Intent documentViewer = new Intent(getContext(),
                            DocumentViewActivity.class);
                    documentViewer.putExtra("url", v.getTag().toString());
                    getContext().startActivity(documentViewer);
                }
            }

            );


            holder.tblLayout.addView(tr);
        }

        return row;
    }

    static class ScholarshipHolder {
        ImageView imgIcon;
        TextView txtTitle;
        TableLayout tblLayout;

    }

}
