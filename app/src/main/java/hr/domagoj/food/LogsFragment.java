package hr.domagoj.food;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.ListFragment;

public class LogsFragment extends ListFragment {

    private ArrayAdapter adapter;
    private ArrayList<DailyLog> list = new ArrayList<>();


    private void loadData() {
        list.clear();
        list.addAll(Utils.getLogs(getActivity()));
        adapter.notifyDataSetChanged();
    }


    public LogsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        adapter = new ArrayAdapter<>(
                inflater.getContext(),
                android.R.layout.simple_list_item_1, list);
        setListAdapter(adapter);


        loadData();

        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {
        super.onListItemClick(l, v, pos, id);
        final DailyLog log = list.get(pos);

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        String message = "";

        for(Meal meal : log.meals){
            message+=meal.name + " " + meal.cal + "cal" +"\n\n";

        }
        alert.setMessage(message);

        alert.setPositiveButton(getString(R.string.ok),
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                        dialogInterface.dismiss();

                    }
                });

        alert.show();
    }
}
