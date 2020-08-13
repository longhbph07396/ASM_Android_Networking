package com.hblong.assigment.model;


import java.util.List;

public class GetCategory {
    public Galleries galleries;


    public class Galleries {
        public List<Gallery> gallery;


        public class Gallery {
            public String gallery_id;
            public Titlee title;
            public Avatar primary_photo_extras;

            public class Titlee {
                public String _content;
            }

            public class Avatar {
                public int height_t,width_t,height_s,width_s,height_m,width_m,height_o,width_o;
                public String url_m,url_t,url_s,url_o;
            }

        }
    }
}
