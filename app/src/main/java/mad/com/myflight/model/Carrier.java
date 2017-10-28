package mad.com.myflight.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by liangze on 29/9/17.
 */

/**
 * contains carrier data
 */

public class Carrier {
    @SerializedName("CarrierId")
    @Expose
    private Integer carrierId;
    @SerializedName("Name")
    @Expose
    private String name;

    public Integer getCarrierId() {
        return carrierId;
    }

    public void setCarrierId(Integer carrierId) {
        this.carrierId = carrierId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
