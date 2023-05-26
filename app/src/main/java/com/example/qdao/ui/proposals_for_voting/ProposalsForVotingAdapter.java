package com.example.qdao.ui.proposals_for_voting;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qdao.R;
import com.example.qdao.ui.my_proposals.ProposalItemsAdapter;

import java.util.List;

import model.ProposalThin;

public class ProposalsForVotingAdapter extends RecyclerView.Adapter<ProposalsForVotingAdapter.ProposalItemViewHolder>  {
    private final ProposalsForVotingAdapter.OnProposalListener listener;
    private List<ProposalThin> proposals;

    public ProposalsForVotingAdapter(List<ProposalThin> proposals, ProposalsForVotingAdapter.OnProposalListener listener){
        this.listener = listener;
        this.proposals = proposals;
    }

    public void setProposals(List<ProposalThin> proposals) {
        this.proposals = proposals;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProposalsForVotingAdapter.ProposalItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(
                        R.layout.proposal_item_layout,
                        parent,
                        false
                );
        return new ProposalItemViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProposalsForVotingAdapter.ProposalItemViewHolder holder, int i) {
        holder.nameTV.setText(proposals.get(i).getName());
        holder.stateTV.setText(getStatusName(proposals.get(i).getState()));
    }

    @Override
    public int getItemCount() {
        if (proposals != null) {
            return proposals.size();
        }
        return 0;
    }

    private String getStatusName(short state) {

        switch (state) {
            case 0: return "Создано";
            case 1: return "Актитвно";
            default: return "Неизвестно";
        }
    }

    public static class ProposalItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView stateTV;
        TextView nameTV;
        ProposalsForVotingAdapter.OnProposalListener listener;

        public ProposalItemViewHolder(@NonNull View itemView,  ProposalsForVotingAdapter.OnProposalListener listener) {
            super(itemView);

            stateTV = itemView.findViewById(R.id.state_tv);
            nameTV = itemView.findViewById(R.id.proposal_name_tv);
            this.listener = listener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onProposalItemClick(getAdapterPosition());
        }
    }

    public interface OnProposalListener {
        void onProposalItemClick(int position);
    }
}
