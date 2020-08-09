package com.hblong.assigment.model;


import java.util.List;

public class GetListImageCallerie {
    public Photos photos;

    public class Photos {
        public List<PhoTo> photo;

        public class PhoTo {
            public String views;
            public String url_m;
            public String url_l;
            public String url_o;
            public int height_m;
            public int height_l;
            public int height_o;
            public int width_o;
            public int width_l;
            public int width_n;

        }
    }
}
