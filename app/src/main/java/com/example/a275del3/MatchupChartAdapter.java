package com.example.a275del3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a275del3.dummy.MatchupChart;

import java.util.List;

public class MatchupChartAdapter extends RecyclerView.Adapter<MatchupChartAdapter.MuHolder> {

    private List<MatchupChart.Chart> mulist;
    private LayoutInflater inflater;
    private OnChartIndClick onChartIndClick;
    private Context context;

    MatchupChartAdapter(List<MatchupChart.Chart> mulist, Context context){
        inflater = LayoutInflater.from(context);
        this.mulist = mulist;
        this.context = context;
        this.onChartIndClick = (OnChartIndClick) context;
    }

    void updateList(List<MatchupChart.Chart> newlist){
        mulist = newlist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = inflater.inflate(R.layout.chart_list_item,parent,false);
        return new MuHolder(view);
    }

    @Override
    public void onBindViewHolder(MuHolder holder, int position){
        holder.textViewTitle.setText(mulist.get(position).content);
        holder.textViewContent.setText(mulist.get(position).details);
    }

    @Override
    public int getItemCount(){
        return mulist.size();
    }

    public class MuHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textViewContent;
        TextView textViewTitle;
        MuHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            textViewContent = itemView.findViewById(R.id.item_text);
            textViewTitle = itemView.findViewById(R.id.tv_title);
        }
        @Override
        public void onClick(View view){
            onChartIndClick.onChartClick(getAdapterPosition());
        }
    }

    public interface OnChartIndClick{
        void onChartClick(int pos);
    }


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_matchup_chart_adapter);
//    }

}
