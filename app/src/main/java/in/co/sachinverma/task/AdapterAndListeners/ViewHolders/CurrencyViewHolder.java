package in.co.sachinverma.task.AdapterAndListeners.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.co.sachinverma.task.Model.Result;
import in.co.sachinverma.task.R;

public class CurrencyViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tvCurrency)
    TextView tvCurrency;
    @BindView(R.id.tvCurrencyLong)
    TextView tvCurrencyLong;
    @BindView(R.id.tvTxFee)
    TextView tvTxFee;

    public CurrencyViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(Result _result, int position) {

        tvCurrency.setText(_result.getCurrency());
        tvCurrencyLong.setText(_result.getCurrencyLong());
        tvTxFee.setText(String.format("%.5f",_result.getTxFee()));
    }

}
