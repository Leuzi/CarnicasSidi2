package ali.software;
import java.util.ArrayList;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ItemAdapter extends ArrayAdapter<Pair<String,String>> {
    public ItemAdapter(Context context, ArrayList<Pair<String,String>> productos) {
       super(context, 0, productos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       // Get the data item for this position
       Pair<String,String> producto = getItem(position);    
       // Check if an existing view is being reused, otherwise inflate the view
       if (convertView == null) {
          convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_detalle, parent, false);
       }
       // Lookup view for data population
       TextView item = (TextView) convertView.findViewById(R.id.item);
       TextView detail = (TextView) convertView.findViewById(R.id.detail);
       // Populate the data into the template view using the data object
       item.setText(producto.first);
       detail.setText(producto.second);
       // Return the completed view to render on screen
       return convertView;
   }
}

