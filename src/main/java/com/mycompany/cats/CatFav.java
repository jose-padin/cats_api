package com.mycompany.cats;

public class CatFav {
    private String id;
    private String image_id;
    private CatImage image;
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    public CatImage getImage() {
        return image;
    }

    public void setImage(CatImage image) {
        this.image = image;
    }

    public void setUrl(String url) {
        this.image.setUrl(url);
    }

    public String getUrl() {
        return image.getUrl();
    }
}

