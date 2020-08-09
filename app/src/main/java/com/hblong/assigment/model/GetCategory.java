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
                public String url_m;
            }

        }
    }
}
