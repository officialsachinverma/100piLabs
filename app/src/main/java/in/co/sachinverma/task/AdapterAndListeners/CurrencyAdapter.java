package in.co.sachinverma.task.AdapterAndListeners;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import in.co.sachinverma.task.AdapterAndListeners.ViewHolders.CurrencyViewHolder;
import in.co.sachinverma.task.Model.Result;
import in.co.sachinverma.task.R;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyViewHolder> {

    private Context _context;
    private ArrayList<Result> _resultArrayList;

    public CurrencyAdapter(Context _context, ArrayList<Result> _resultArrayList) {
        this._context = _context;
        this._resultArrayList = _resultArrayList;
    }

    @Override
    public CurrencyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_currency_item, parent,false);
        return new CurrencyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CurrencyViewHolder holder, int position) {
        Result _result = _resultArrayList.get(position);
        holder.bind(_result, position);
    }

    @Override
    public int getItemCount() {
        return _resultArrayList.size();
    }

}
