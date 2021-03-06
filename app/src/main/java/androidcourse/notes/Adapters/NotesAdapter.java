
package androidcourse.notes.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidcourse.notes.Models.Note;
import androidcourse.notes.R;

public class NotesAdapter extends ArrayAdapter<Note> {
    private Context mContext;
    private List<Note> mNotelist;
    private int mLayoutResourceId;

    private static class ViewHolder {
        TextView title;
        TextView date;
        ImageView img;
        ImageView pwd;
        ImageView attach;
    }

    public NotesAdapter(Context context, int layoutResourceId, List<Note> notelist) {
        super(context, layoutResourceId, notelist);
        mContext = context;
        mNotelist = notelist;
        mLayoutResourceId = layoutResourceId;
    }

    @Override
    public int getCount() {
        return mNotelist.size();
    }

    @Override
    public Note getItem(int position) {
        return mNotelist.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View rowView = convertView;
        /* There is no view at this position, we create a new one.
                In this case by inflating an xml layout */
        if (rowView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            rowView = inflater.inflate(mLayoutResourceId, parent, false);
            // configure view holder
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) rowView.findViewById(R.id.title);
            viewHolder.date = (TextView) rowView.findViewById(R.id.lastModified);
            viewHolder.img = (ImageView) rowView.findViewById(R.id.icon);
            viewHolder.pwd = (ImageView) rowView.findViewById(R.id.pwdImg);
            viewHolder.attach = (ImageView) rowView.findViewById(R.id.attachment);
            rowView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) rowView.getTag();
        }
        //fill data

        viewHolder.title.setText(mNotelist.get(position).getmTitle());
        viewHolder.date.setText(mNotelist.get(position).dateFormatted());

        boolean hasPassword = mNotelist.get(position).getmPassword() != null;
        boolean hasAttachament = mNotelist.get(position).getPhotoPath() != null;

        if (hasPassword && hasAttachament) {
            viewHolder.pwd.setImageResource(R.drawable.password);
            viewHolder.attach.setImageResource(R.drawable.image_attachment);
        } else if (hasPassword) {
            viewHolder.pwd.setImageResource(R.drawable.password);
        } else if (hasAttachament) {
            viewHolder.pwd.setImageResource(R.drawable.image_attachment);
            viewHolder.attach.setImageDrawable(null);
        } else {
            viewHolder.attach.setImageDrawable(null);
            viewHolder.pwd.setImageDrawable(null);
        }
        return rowView;
    }
        /*
            if (mNotelist.get(position).getmPassword() != null) {
                viewHolder.pwd.setImageResource(R.drawable.password);
            } else {
                viewHolder.pwd.setImageDrawable(null);
            }
            return rowView;
        }*/
    }