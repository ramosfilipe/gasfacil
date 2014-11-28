package boleiros.gas_facil.modelo;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

/**
 * Created by filipe on 28/10/14.
 */

@ParseClassName("Produto")
public class Produto extends ParseObject {


    public Produto() {
    }

    public String getPrice() {
        return getString("price");
    }

    public void setPrice(String price) {
        put("price", price);
    }

    public String getType() {
        return getString("type");
    }

    public void setType(String type) {
        put("type", type);
    }

    public ParseFile getPhotoFile() {
        return getParseFile("photo");
    }

    public void setPhotoFile(ParseFile file) {
        put("photo", file);
    }

    public ParseFile getThumbnailBlurFile() {
        return getParseFile("thumbnailBlur");
    }

    public void setThumbnailBlurFile(ParseFile file) {
        put("thumbnailBlur", file);
    }

}