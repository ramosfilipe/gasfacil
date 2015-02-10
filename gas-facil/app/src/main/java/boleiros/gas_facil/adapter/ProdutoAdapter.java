package boleiros.gas_facil.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseFile;

import java.lang.ref.WeakReference;
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
    private Bitmap mPlaceHolderBitmap;
    private LruCache<String, Bitmap> mMemoryCache;

    public ProdutoAdapter(List<Produto> produtos, int rowLayout, Context context) {
        this.produtos = produtos;
        this.rowLayout = rowLayout;
        this.mContext = context;
        mPlaceHolderBitmap = decodeSampledBitmapFromResource(context.getResources(),
                R.drawable.placeholder, 300, 300);
        // Get memory class of this device, exceeding this amount will throw an
        // OutOfMemory exception.
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        // Use 1/8th of the available memory for this memory cache.
        final int cacheSize = maxMemory / 8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in bytes rather than number
                // of items.
                if (Integer.valueOf(android.os.Build.VERSION.SDK_INT) >= 12) {
                    return bitmap.getByteCount();
                } else {
                    return (bitmap.getRowBytes() * bitmap.getHeight());
                }
            }

        };
    }

    public ProdutoAdapter(List<Produto> produtos, int rowLayout) {
        this.produtos = produtos;
        this.rowLayout = rowLayout;
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

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static boolean cancelPotentialWork(int data, ImageView imageView) {
        final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);
        if (bitmapWorkerTask != null) {
            final int bitmapData = bitmapWorkerTask.data;
            if (bitmapData != data) {
                // Cancel previous task
                bitmapWorkerTask.cancel(true);
            } else {
                // The same work is already in progress
                return false;
            }
        }
        // No task associated with the ImageView, or an existing task was
        // cancelled
        return true;
    }

    private static BitmapWorkerTask getBitmapWorkerTask(ImageView imageView) {
        if (imageView != null) {
            final Drawable drawable = imageView.getDrawable();
            if (drawable instanceof AsyncDrawable) {
                final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
                return asyncDrawable.getBitmapWorkerTask();
            }
        }
        return null;
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
        String aux = "R$" + produto.getPrice();
        viewHolder.precoProduto.setText(aux.replace(".",","));
        ParseFile pf = produto.getPhotoFile();
        loadBitmap(pf, viewHolder.produtoImage);
//        try {
//            viewHolder.produtoImage.setImageBitmap(decodeSampledBitmapFromResource(produto.getPhotoFile(), 1000, 1000));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public int getItemCount() {
        return produtos == null ? 0 : produtos.size();
    }

    public void loadBitmap(ParseFile pf, ImageView imageView) {
        if (cancelPotentialWork(pf.hashCode(), imageView)) {
            final BitmapWorkerTask task = new BitmapWorkerTask(imageView);
            final AsyncDrawable asyncDrawable =
                    new AsyncDrawable(mContext.getResources(), mPlaceHolderBitmap, task);
            imageView.setImageDrawable(asyncDrawable);
            task.execute(pf);
        }
    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return (Bitmap) mMemoryCache.get(key);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView = null;
        public TextView countryName;
        public ImageView produtoImage;
        public TextView precoProduto;

        public ViewHolder(View itemView) {
            super(itemView);
            countryName = (TextView) itemView.findViewById(R.id.countryName);
            precoProduto = (TextView) itemView.findViewById(R.id.precoProduto);
            produtoImage = (ImageView) itemView.findViewById(R.id.produtoImagem);
        }

    }

    static class AsyncDrawable extends BitmapDrawable {
        private final WeakReference<BitmapWorkerTask> bitmapWorkerTaskReference;

        public AsyncDrawable(Resources res, Bitmap bitmap,
                             BitmapWorkerTask bitmapWorkerTask) {
            super(res, bitmap);
            bitmapWorkerTaskReference =
                    new WeakReference<BitmapWorkerTask>(bitmapWorkerTask);
        }

        public BitmapWorkerTask getBitmapWorkerTask() {
            return bitmapWorkerTaskReference.get();
        }
    }

    class BitmapWorkerTask extends AsyncTask<ParseFile, Void, Bitmap> {
        private final WeakReference<ImageView> imageViewReference;
        public int data = 0;

        public BitmapWorkerTask(ImageView imageView) {
            // Use a WeakReference to ensure the ImageView can be garbage
            // collected
            imageViewReference = new WeakReference<ImageView>(imageView);
        }

        // Decode image in background.
        @Override
        protected Bitmap doInBackground(ParseFile... params) {
            data = params[0].hashCode();
            Bitmap bitmap = null;
            if (getBitmapFromMemCache(String.valueOf(params[0])) != null) {
                return getBitmapFromMemCache(String.valueOf(params[0]));
            }
            try {
                //bitmap  = BitmapFactory.decodeByteArray(params[0].getData(), 0,
                // params[0].getData().length);
                bitmap = decodeSampledBitmapFromResource(params[0], 650, 650);
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            addBitmapToMemoryCache(String.valueOf(params[0]), bitmap);
            return bitmap;
        }

        // Once complete, see if ImageView is still around and set bitmap.
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (isCancelled()) {
                bitmap = null;
            }
            if (imageViewReference != null && bitmap != null) {
                final ImageView imageView = imageViewReference.get();
                if (imageView != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }
}