package com.wimso.cervatify.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wimso.cervatify.*;
import com.wimso.cervatify.listener.RecyclerItemClickListener;
import com.wimso.cervatify.model.Cerveja;
import com.wimso.cervatify.widget.LetterTile;

import java.util.ArrayList;
import java.util.List;

public class CervejaListAdapter extends RecyclerView.Adapter<CervejaListAdapter.CervejaHolder>{

    private List<Cerveja> cervejaList;
    private Context context;

    private RecyclerItemClickListener recyclerItemClickListener;

    public CervejaListAdapter(Context context) {
        this.context = context;
        this.cervejaList = new ArrayList<>();
    }

    private void add(Cerveja item) {
        cervejaList.add(item);
        notifyItemInserted(cervejaList.size() - 1);
    }

    public void addAll(List<Cerveja> cervejaList) {
        for (Cerveja cerveja : cervejaList) {
            add(cerveja);
        }
    }

    public void remove(Cerveja item) {
        int position = cervejaList.indexOf(item);
        if (position > -1) {
            cervejaList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public Cerveja getItem(int position) {
        return cervejaList.get(position);
    }

    @Override
    public CervejaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_cerveja_item, parent, false);

        final CervejaHolder cervejaHolder = new CervejaHolder(view);

        cervejaHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPos = cervejaHolder.getAdapterPosition();
                if (adapterPos != RecyclerView.NO_POSITION) {
                    if (recyclerItemClickListener != null) {
                        recyclerItemClickListener.onItemClick(adapterPos, cervejaHolder.itemView);
                    }
                }
            }
        });

        return cervejaHolder;
    }

    @Override
    public void onBindViewHolder(CervejaHolder holder, int position) {
        final Cerveja cerveja = cervejaList.get(position);

        final Resources res = context.getResources();
        final int tileSize = res.getDimensionPixelSize(R.dimen.letter_tile_size);

        LetterTile letterTile = new LetterTile(context);

        Bitmap letterBitmap = letterTile.getLetterTile(cerveja.getNome(),
                String.valueOf(cerveja.getId()), tileSize, tileSize);

        holder.thumb.setImageBitmap(letterBitmap);
        holder.nome.setText(cerveja.getNome());
        holder.preco.setText(cerveja.getPreco());
        holder.local.setText(cerveja.getLocal());
    }

    @Override
    public int getItemCount() {
        return cervejaList.size();
    }

    public void setOnItemClickListener(RecyclerItemClickListener recyclerItemClickListener) {
        this.recyclerItemClickListener = recyclerItemClickListener;
    }

    static class CervejaHolder extends RecyclerView.ViewHolder {

        ImageView thumb;
        TextView nome;
        TextView preco;
        TextView local;

        public CervejaHolder(View itemView) {
            super(itemView);

            thumb = (ImageView) itemView.findViewById(R.id.thumb);
            nome = (TextView) itemView.findViewById(R.id.nome);
            preco = (TextView) itemView.findViewById(R.id.preco);
            local = (TextView) itemView.findViewById(R.id.local);

        }
    }
}
