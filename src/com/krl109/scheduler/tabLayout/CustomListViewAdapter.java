package com.krl109.scheduler.tabLayout;

import java.util.List;

import com.krl109.scheduler.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class CustomListViewAdapter extends ArrayAdapter<Schedule> {
	 
    Context context;
 
    public CustomListViewAdapter(Context context, int resourceId,
            List<Schedule> items) {
        super(context, resourceId, items);
        this.context = context;
    }
 
    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
        TextView txtDateTime;
        TextView txtRecipient;
        TextView txtContent;
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        Schedule rowItem = getItem(position);
 
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_schedule, null);
            holder = new ViewHolder();
            holder.txtRecipient = (TextView) convertView.findViewById(R.id.recipientNumbers);
            holder.txtContent = (TextView) convertView.findViewById(R.id.contentMessage);
            holder.txtDateTime = (TextView) convertView.findViewById(R.id.dateTimeSch);
            holder.imageView = (ImageView) convertView.findViewById(R.id.icon);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
 
        holder.txtRecipient.setText(rowItem.getRecipientNumbers());
        holder.txtContent.setText(rowItem.getContentMessages());
        holder.txtDateTime.setText(rowItem.getDateTimeSch());
        holder.imageView.setImageResource(rowItem.getImageId());
 
        return convertView;
    }
}
