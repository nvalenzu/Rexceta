package cl.telematica.android.rexceta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Jesi on 29-11-16.
 */

public class DrawerListAdapter extends ArrayAdapter {

    public DrawerListAdapter(Context context, List objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)parent.getContext().
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.drawer_login_adapter, null);
        }


        ImageView icon = (ImageView) convertView.findViewById(R.id.icon);
        TextView name = (TextView) convertView.findViewById(R.id.name);

        DrawerItem item = (DrawerItem) getItem(position);
        icon.setImageResource(item.getIconId());
        name.setText(item.getName());

        return convertView;
    }
}