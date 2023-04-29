package com.example.qdao.ui.my_proposals;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qdao.R;

import java.util.List;

import model.ProposalListDto;

public class ProposalItemsAdapter extends RecyclerView.Adapter<ProposalItemsAdapter.ProposalItemViewHolder> {

    private OnProposalListener listener;
    private List<ProposalListDto> proposals;

    public ProposalItemsAdapter(List<ProposalListDto> proposals, OnProposalListener listener) {
        this.listener = listener;
        this.proposals = proposals;
    }

    public void setProposals(List<ProposalListDto> proposals) {
        this.proposals = proposals;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProposalItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(
                        R.layout.proposal_item_layout,
                        parent,
                        false
                );
        return new ProposalItemViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProposalItemViewHolder holder, int i) {
        holder.nameTV.setText(proposals.get(i).getName());
    }

    @Override
    public int getItemCount() {
        if (proposals != null) {
            return proposals.size();
        }
        return 0;
    }

    public static class ProposalItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Button stateImage;
        TextView nameTV;

        OnProposalListener listener;

        public ProposalItemViewHolder(@NonNull View itemView, OnProposalListener listener) {
            super(itemView);

            stateImage = itemView.findViewById(R.id.state_btn);
            nameTV = itemView.findViewById(R.id.proposal_name_tv);
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
