package com.example.bt_dbo4o.Activity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bt_dbo4o.Model.Note;
import com.example.bt_dbo4o.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

class NoteAdapter extends BaseAdapter {
    final List<Note> listNote;

    NoteAdapter(List<Note> listNote) {
        this.listNote = listNote;
    }

    @Override
    public int getCount() {
        return listNote.size();
    }

    @Override
    public Object getItem(int i) {
        return listNote.get(i);
    }

    @Override
    public long getItemId(int i) {
        return listNote.get(i).getID();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View viewNote;
        if (view == null) {
            viewNote = View.inflate(viewGroup.getContext(), R.layout.ghi_chu, null);
        } else viewNote = view;

        //Bind sữ liệu phần tử vào View
        Note note = (Note) getItem(i);
        ((TextView) viewNote.findViewById(R.id.contTitle)).setText(note.TITLE);
        ((TextView) viewNote.findViewById(R.id.contContent)).setText(note.CONTENT);
        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss dd/mm/yyyy ");
        String strDate = dateFormat.format(note.CREATEDATE);
        ((TextView) viewNote.findViewById(R.id.lbNgay)).setText(strDate);
        return viewNote;
    }
}
