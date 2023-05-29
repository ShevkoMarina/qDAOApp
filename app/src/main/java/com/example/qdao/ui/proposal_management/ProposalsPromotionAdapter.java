package com.example.qdao.ui.proposal_management;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qdao.R;

import java.util.List;

import model.ProposalThin;

public class ProposalsPromotionAdapter extends RecyclerView.Adapter<ProposalsPromotionAdapter.ProposalItemViewHolder>{
    private ProposalsPromotionAdapter.OnProposalListener listener;
    private List<ProposalThin> proposals;

    public ProposalsPromotionAdapter(List<ProposalThin> proposals, ProposalsPromotionAdapter.OnProposalListener listener) {
        this.listener = listener;
        this.proposals = proposals;
    }

    public void setProposals(List<ProposalThin> proposals) {
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
        return new ProposalsPromotionAdapter.ProposalItemViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProposalItemViewHolder holder, int i) {
        holder.nameTV.setText(proposals.get(i).getName());
        holder.stateTV.setText(getStatusName(proposals.get(i).getState()));
    }

    // Перенести в модель
    private String getStatusName(short state) {

        switch (state) {
            case 4: return "Нет кворума";
            case 5: return "Принято";
            case 6: return "В ожидании";
            case 7: return "В очереди";
            default: return "Неизвестно";
        }
    }

    @Override
    public int getItemCount() {
        if (proposals != null) {
            return proposals.size();
        }
        return 0;
    }

    public static class ProposalItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView stateTV;
        TextView nameTV;
        ProposalsPromotionAdapter.OnProposalListener listener;

        public ProposalItemViewHolder(@NonNull View itemView, ProposalsPromotionAdapter.OnProposalListener listener) {
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
