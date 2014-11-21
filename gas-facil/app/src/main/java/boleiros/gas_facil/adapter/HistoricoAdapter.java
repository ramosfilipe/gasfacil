package boleiros.gas_facil.adapter;

/**
 * Created by filipe on 20/11/14.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseFile;

import java.util.Date;
import java.util.List;

import boleiros.gas_facil.R;
import boleiros.gas_facil.modelo.Pedido;
import boleiros.gas_facil.modelo.Produto;




public class HistoricoAdapter extends RecyclerView.Adapter<HistoricoAdapter.ViewHolder> {


    private List<Pedido> pedidos;
    private int rowLayout, quantidade;
    private Context mContext;

    public HistoricoAdapter(List<Pedido> pedidos, int rowLayout) {
        this.pedidos = pedidos;
        this.rowLayout = rowLayout;
       // this.mContext = context;
       // this.quantidade = quantidade;
    }

    public static Bitmap decodeSampledBitmapFromResource(ParseFile pf,
                                                         int reqWidth,
                                                         int reqHeight) throws ParseException {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(pf.getData(), 0, pf.getData().length, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(pf.getData(), 0, pf.getData().length, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }
    private String formartaStringData(Date data) {
        String minutoAdicionadoComZero = "";

        if (data.getMinutes() < 10) {
            minutoAdicionadoComZero = "0" + data.getMinutes();
        } else {
            minutoAdicionadoComZero = "" + data.getMinutes();
        }

        return "" + data.getDate() + "/" + (data.getMonth() + 1) + " às " + data.getHours() + ":" +
                minutoAdicionadoComZero + "h";
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Produto prod = (Produto) pedidos.get(i).getProduto();

        viewHolder.dataCompra.setText(" "+formartaStringData(pedidos.get(i).getCreatedAt()));
        viewHolder.precoCompra.setText(" "+pedidos.get(i).getPrice()+" reais");
        viewHolder.statusCompra.setText(" "+pedidos.get(i).getStatus());
        viewHolder.qtd.setText(" "+pedidos.get(i).getQuantidade());

        try {
            viewHolder.produtoImage.setImageBitmap(decodeSampledBitmapFromResource(prod.getPhotoFile(), 1000, 1000));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return pedidos == null ? 0 : pedidos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView dataCompra;
        public ImageView produtoImage;
        public TextView precoCompra;
        public TextView statusCompra;
        public TextView qtd;



        public ViewHolder(View itemView) {
            super(itemView);
            qtd = (TextView) itemView.findViewById(R.id.textViewQuantidade);
            dataCompra = (TextView) itemView.findViewById(R.id.textViewDataPedido);
            statusCompra = (TextView) itemView.findViewById(R.id.textViewStatus);
            precoCompra = (TextView) itemView.findViewById(R.id.textViewValorCompra);
            produtoImage = (ImageView) itemView.findViewById(R.id.item_icon);
        }

    }

}