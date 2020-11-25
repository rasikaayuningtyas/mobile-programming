package id.ac.ui.cs.mobileprogramming.rasikaayuningtyas.winit;

import java.util.List;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private List<MainData> dataList;
    private Activity context;
    private RoomDB database;

    public MainAdapter(Activity context, List<MainData> dataList){
        this.context = context;
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_main, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        MainData data = dataList.get(position);
        database = RoomDB.getInstance(context);
        holder.textView.setText(data.getGroup());
        holder.arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainData d = dataList.get(holder.getAdapterPosition());
                int sID = d.getID();
                String groupName = d.getGroup();
                int streamingNumber = d.getStreamingNumber();
                int votingNumber = d.getVotingNumber();
                int mentionNumber = d.getMentionNumber();
                int currentStream = d.getCurrentStream();
                int currentVote = d.getCurrentVoting();
                int currentMention = d.getCurrentMention();

                Intent intent = new Intent(context, Activity2.class);
                intent.putExtra("sID", sID);
                intent.putExtra("name", groupName);
                intent.putExtra("streaming", streamingNumber);
                intent.putExtra("voting", votingNumber);
                intent.putExtra("mention", mentionNumber);
                intent.putExtra("currentStream", currentStream);
                intent.putExtra("currentVote", currentVote);
                intent.putExtra("currentMention", currentMention);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView arrow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.text_view);
            arrow = itemView.findViewById(R.id.arrow);


        }
    }
}
