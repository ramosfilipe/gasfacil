package boleiros.gas_facil.adapter;
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

import java.util.List;

import boleiros.gas_facil.R;
import boleiros.gas_facil.modelo.Produto;

/**
 * Created by filipe on 28/10/14.
 */
public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.ViewHolder>{

    private List<Produto> produtos;
    private int rowLayout;
    private Context mContext;

    public ProdutoAdapter(List<Produto> produtos, int rowLayout, Context context) {
        this.produtos = produtos;
        this.rowLayout = rowLayout;
        this.mContext = context;
    }
    public ProdutoAdapter(List<Produto> produtos, int rowLayout) {
        this.produtos = produtos;
        this.rowLayout = rowLayout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Produto produto = produtos.get(i);
        //viewHolder.countryName.setText(produto.getType());
        viewHolder.precoProduto.setText("R$"+produto.getPrice());

        try {
            viewHolder.produtoImage.setImageBitmap(decodeSampledBitmapFromResource(produto.getPhotoFile(),1000,1000));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return produtos == null ? 0 : produtos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView countryName;
        public ImageView produtoImage;
        public TextView precoProduto;

        public ViewHolder(View itemView) {
            super(itemView);
            countryName = (TextView) itemView.findViewById(R.id.countryName);
            precoProduto = (TextView) itemView.findViewById(R.id.precoProduto);

            produtoImage = (ImageView)itemView.findViewById(R.id.produtoImagem);
        }

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

}