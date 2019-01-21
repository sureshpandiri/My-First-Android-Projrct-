package com.example.chandu.attendapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SubjectAdapter extends ListAdapter<Subject, SubjectAdapter.SubjectHolder> {

    //private List<Subject> subs= new ArrayList<>();
    private onCilckItemListener listener, listener2;

    private SubjectViewModel viewModel;

    private Context mcontext;


    public SubjectAdapter(Context mcontext) {
        super(DIFF_CALLBACK);
       this.mcontext = mcontext;


    }

    private static final DiffUtil.ItemCallback<Subject> DIFF_CALLBACK = new DiffUtil.ItemCallback<Subject>() {
        @Override
        public boolean areItemsTheSame(Subject oldItem, Subject newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(Subject oldItem, Subject newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getTotal() == newItem.getTotal() &&
                    oldItem.getAttended() == newItem.getAttended();
        }
    };

    @NonNull
    @Override
    public SubjectHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.subject_item, parent, false);
        return new SubjectHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final SubjectHolder holder, int position) {
        Subject currentSubject = getItem(position);
        holder.tvTitle.setText(currentSubject.getTitle());
        holder.tvAttend.setText(String.valueOf(currentSubject.getAttended()));
        holder.tvTotal.setText(String.valueOf(currentSubject.getTotal()));
        holder.itemView.setLongClickable(true);

        int lastPosition = -1;
        Animation animation = AnimationUtils.loadAnimation(mcontext,
                (position > lastPosition) ? R.anim.up_from_bottom
                        : R.anim.down_from_top);
        holder.itemView.startAnimation(animation);
        lastPosition = position;


    }


    public Subject subjectAt(int position) {
        return getItem(position);
    }

    class SubjectHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private TextView tvAttend;
        private TextView tvTotal;
        private TextView tvoption;
        public RelativeLayout fg, bg;

        public SubjectHolder(@NonNull final View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.title_tv);
            tvAttend = itemView.findViewById(R.id.attend_tv);
            tvTotal = itemView.findViewById(R.id.total_tv);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.itemClick(getItem(position));
                    }
                }
            });


        }
    }

    public interface onCilckItemListener {
        void itemClick(Subject subject);
    }



    public void setOnClickItemListener(onCilckItemListener listener) {
        this.listener = listener;
    }

}
