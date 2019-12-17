package edu.gatech.seclass.crypto6300;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class UnsolvedCryptogramAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public interface CryptogramSelectListener{
        void onCryptogramSelected(Cryptogram cryptogram);
    }
    private List<Cryptogram> mCryptogramList;
    private Context mContext;
    private CryptogramSelectListener mListener;
    private int mUnstartedPos;

    public UnsolvedCryptogramAdapter(
            List<Cryptogram> cryptogramList,
            int unstartedPos,
            CryptogramSelectListener listener){
        mCryptogramList = cryptogramList;
        mListener = listener;
        mUnstartedPos = unstartedPos;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View itemView = layoutInflater.inflate(R.layout.unsolved_cryptogram, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (position>= mUnstartedPos) {
            ((ViewHolder) holder).bindData(mCryptogramList.get(position), Status.INPROGRESS);
        } else {
            ((ViewHolder) holder).bindData(mCryptogramList.get(position), Status.UNSTARTED);
        }
    }

    @Override
    public int getItemCount() {
        return mCryptogramList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView item;
        public ViewHolder(View itemView){
            super(itemView);
            item = (TextView) itemView.findViewById(R.id.unsolved_cryptogram_view);
        }

        public void bindData(Cryptogram cryptogram, Status status){
            String title = cryptogram.getName();
            String spacing = "  ";
            SpannableStringBuilder builder = new SpannableStringBuilder(title+spacing+status.name());
            ForegroundColorSpan orangeSpan =
                    new ForegroundColorSpan(mContext.getResources().getColor(R.color.color_orange));
            ForegroundColorSpan statusSpan =
                    new ForegroundColorSpan(getColorForStatus(status));
            int textLength = title.length()+spacing.length()+status.name().length();
            builder.setSpan(orangeSpan, 0, title.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            builder.setSpan(statusSpan, textLength-status.name().length(), textLength, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            item.setText(builder);
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    mListener.onCryptogramSelected(mCryptogramList.get(pos));
                }
            });
        }

        private int getColorForStatus(Status status){
            switch (status){
                case UNSTARTED:
                    return mContext.getResources().getColor(R.color.color_red);
                case INPROGRESS:
                    return mContext.getResources().getColor(R.color.color_green);
            }
            return 0;
        }
    }

}
