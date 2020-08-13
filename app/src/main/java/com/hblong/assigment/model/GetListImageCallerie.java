package com.hblong.assigment.model;


import java.io.Serializable;
import java.util.List;

public class GetListImageCallerie {
    public Photos photos;

    public class Photos {
        public List<PhoTo> photo;

        public class PhoTo implements Serializable {
            public int width_l, height_l, width_sq, height_sq, width_t, height_t, width_s, height_s, width_q, height_q, width_m, height_m, width_n, height_n, width_z, height_z, width_c, height_c, width_o, height_o;
            public String views, media, path_alias, url_sq, url_t, url_s, url_q, url_m, url_n, url_z, url_c, url_l, url_o;

            public String[] getLink() {
                return new String[]{url_sq, url_t, url_s, url_q, url_m, url_n, url_z, url_c, url_l, url_o};
            }
        }
    }
}
