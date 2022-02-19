/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cats;
import io.github.cdimascio.dotenv.Dotenv;
/**
 *
 * @author jose
 */
public class Cat {

    public Cat() {}

    public Cat(String id, String url) {
        this.id = id;
        this.url = url;
    }

    Dotenv dotenv = Dotenv.load();

    private String id;
    private String api_key = dotenv.get("CAT_API_KEY");
    private String image;
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApi_key() {
        return api_key;
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
