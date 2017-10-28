package mad.com.myflight.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * contains locale data
 */

public class Locale {


        @SerializedName("Code")
        @Expose
        private String code;
        @SerializedName("Name")
        @Expose
        private String name;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    @Override
    public String toString() {
        return "Locale{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
