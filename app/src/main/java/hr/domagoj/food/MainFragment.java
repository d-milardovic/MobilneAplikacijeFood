package hr.domagoj.food;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.ListFragment;

public class MainFragment extends ListFragment {

    private ArrayAdapter adapter;
    private ArrayList<Meal> list = new ArrayList<>();


    void loadData() {
        list.clear();
        list.addAll(Utils.getMeals(getActivity()));
        adapter.notifyDataSetChanged();
    }


    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {
        super.onListItemClick(l, v, pos, id);
        final Meal meal = list.get(pos);

        int todayCal = Utils.getTodayCal(getActivity());
        int dailyLimit = Utils.getDailyLimit(getActivity());
        int remainingCal = dailyLimit - todayCal;

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

        if(dailyLimit == 0) {
            alert.setMessage("Zabilježiti pojedeni obrok?\n\n" +
                    "Niste postavili dnevni limit.");
        } else if (remainingCal >= 0){
            alert.setMessage("Zabilježiti pojedeni obrok? \n\n" +
                    "Danas ste unjeli " + todayCal + "cal.\n" +
                    "Još " + remainingCal + " do dnevnog limita.");
        } else {
            alert.setMessage("Zabilježiti pojedeni obrok? \n\n" +
                    "Danas ste unjeli " + todayCal + "cal.\n" +
                    "PREKORAČILI STE DNEVNI LIMIT!");
        }

        alert.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        Utils.addToLog(meal, getActivity());
                        dialogInterface.dismiss();

                    }
                });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alert.show();
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


}
