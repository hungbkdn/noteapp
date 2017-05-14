package com.example.admin.bknote;



import android.app.Activity;

import android.content.Context;

import android.view.Gravity;

import android.view.LayoutInflater;

import android.view.View;

import android.view.ViewGroup;

import android.widget.BaseAdapter;

import android.widget.LinearLayout;

import android.widget.RelativeLayout;

import android.widget.TextView;



import java.util.List;



public class NoteAdapter extends BaseAdapter {



    private final List<Note> note;

    private Activity context;



    public NoteAdapter(Activity context, List<Note> note) {

        this.context = context;

        this.note = note;

    }



    @Override

    public int getCount() {

        if (note != null) {

            return note.size();

        } else {

            return 0;

        }

    }



    @Override

    public Note getItem(int position) {

        if (note != null) {

            return note.get(position);

        } else {

            return null;

        }

    }



    @Override

    public long getItemId(int position) {

        return position;

    }



    @Override

    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        Note chatMessage = getItem(position);

        LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);



        if (convertView == null) {

            convertView = vi.inflate(R.layout.list_row, null);

            holder = createViewHolder(convertView);

            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();

        }



        holder.txtMessage.setText(chatMessage.getNote());

        holder.txtInfo.setText(chatMessage.toString());



        return convertView;

    }



    public void add(Note notes) {

        note.add(notes);

    }

    public void update(Note notes, int position)

    {

        note.remove(position);

        note.add(position, notes);

    }

    public void remove(int position)

    {

        note.remove(position);

    }



    public void add(List<Note> messages) {

        note.addAll(messages);

    }





    private ViewHolder createViewHolder(View v) {

        ViewHolder holder = new ViewHolder();

        holder.txtMessage = (TextView) v.findViewById(R.id.note);

        holder.thumbnail = (LinearLayout) v.findViewById(R.id.thumbnail);

        holder.txtInfo = (TextView) v.findViewById(R.id.time);

        return holder;

    }



    private static class ViewHolder {

        public TextView txtMessage;

        public TextView txtInfo;

        public LinearLayout thumbnail;

    }

}