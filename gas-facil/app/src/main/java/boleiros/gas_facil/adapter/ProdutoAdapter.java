package boleiros.gas_facil.adapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import boleiros.gas_facil.R;
import boleiros.gas_facil.modelo.Produto;

/**
 * Created by filipe on 28/10/14.
 */
public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.ViewHolder> {

    private List<Produto> produtos;
    private int rowLayout;
    private Context mContext;

    public ProdutoAdapter(List<Produto> produtos, int rowLayout, Context context) {
        this.produtos = produtos;
        this.rowLayout = rowLayout;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Produto produto = produtos.get(i);
        viewHolder.countryName.setText(produto.getType());
    }

    @Override
    public int getItemCount() {
        return produtos == null ? 0 : produtos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView countryName;
        public ImageView countryImage;

        public ViewHolder(View itemView) {
            super(itemView);
            countryName = (TextView) itemView.findViewById(R.id.countryName);
            countryImage = (ImageView)itemView.findViewById(R.id.countryImage);
        }

    }
}