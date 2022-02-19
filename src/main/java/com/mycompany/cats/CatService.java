package com.mycompany.cats;

import com.google.gson.Gson;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.ByteString;
import org.json.*;

public class CatService {

    public static String URL = "https://api.thecatapi.com/v1";

    public static void getCat() throws IOException {
        OkHttpClient client = new OkHttpClient();
        String url = URL + "/images/search";
        Request request;
        Response response;
        request = new Request.Builder().url(url).get().build();
        response = client.newCall(request).execute();
        
        String response_json = response.body().string();
        response_json = response_json.substring(1, response_json.length());
        response_json = response_json.substring(0, response_json.length() - 1);

        JSONObject jobj = new JSONObject(response_json);
        String id = jobj.getString("id");
        String _url = jobj.getString("url");
        Cat cat = new Cat(id, _url);

        Image image = null;
        try {

            URL cat_url = new URL(cat.getUrl());
            HttpURLConnection httpcon = (HttpURLConnection) cat_url.openConnection();
            httpcon.addRequestProperty("User-Agent", "");
            BufferedImage buff = ImageIO.read(httpcon.getInputStream());
            ImageIcon backgroundCat = new ImageIcon(buff);

            if (backgroundCat.getIconWidth() > 800) {
                Image background = backgroundCat.getImage();
                Image modified;
                modified = background.getScaledInstance(
                    800, 600, java.awt.Image.SCALE_SMOOTH
                );
                backgroundCat = new ImageIcon(modified);
            }

            String menu = "Options: \n"
                + "1. See another image\n"
                + "2. Mark as fav\n"
                + "3. See a random pic from favs\n"
                + "4. Exit";
            String[] buttons = {
                "See other cat", "Mark as fav", "See random fav", "Exit"
            };
            String option = (String) JOptionPane.showInputDialog(
                null, menu, cat.getId(), JOptionPane.INFORMATION_MESSAGE,
                backgroundCat, buttons, buttons[0]
            );

            int selected = -1;

            if (option != null) {
                for (int i = 0; i < buttons.length; i++) {
                    if (option.equals(buttons[i])) {
                        selected = i;
                    }
                }
            }

            switch(selected) {
                case 0:
                    getCat();
                    break;
                case 1:
                    markFavouriteCat(cat, client);
                    break;
                case 2:
                    getFavRandomPic(cat.getApi_key(), client);
                    break;
                default:
                    break;
            }

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void markFavouriteCat(Cat cat, OkHttpClient client) {
        String url = URL + "/favourites";
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(
            mediaType, "{\n\"image_id\": \"" + cat.getId() + "\"\n}"
        );
        Request request = new Request.Builder().url(url).method(
            "POST", body).addHeader(
            "x-api-key", cat.getApi_key()).addHeader(
            "Content-Type", "application/json").build();
        try {
            Response response = client.newCall(request).execute();
            System.out.println("Marked as favourite!");
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void getFavRandomPic(String api_key, OkHttpClient client) throws IOException {
        Request request = new Request.Builder().url(URL + "/favourites").method(
                "GET", null).addHeader("x-api-key", api_key).build();
        Response response = client.newCall(request).execute();

        String response_json = response.body().string();

        Gson gson = new Gson();
        CatFav[] catsList = gson.fromJson(response_json, CatFav[].class);

        if (catsList.length > 0) {
            int min = 1;
            int max = catsList.length;
            int rdmNumber = min + (int) (Math.random() * ((max - min) + 1));
            int idx = rdmNumber - 1;

            CatFav catFav = catsList[idx];

            Image image = null;
            try {
                URL cat_url = new URL(catFav.getUrl());
                HttpURLConnection httpcon = (HttpURLConnection) cat_url.openConnection();
                httpcon.addRequestProperty("User-Agent", "");
                BufferedImage buff = ImageIO.read(httpcon.getInputStream());
                ImageIcon backgroundCat = new ImageIcon(buff);

                if (backgroundCat.getIconWidth() > 800) {
                    Image background = backgroundCat.getImage();
                    Image modified;
                    modified = background.getScaledInstance(
                        800, 600, java.awt.Image.SCALE_SMOOTH
                    );
                    backgroundCat = new ImageIcon(modified);
                }

                String menu = "Options: \n"
                    + "1. See another image\n"
                    + "2. Delete one pic from favs\n"
                    + "3. Exit";
                String[] buttons = {
                    "See other cat", "Del fav cat", "Exit"
                };
                String option = (String) JOptionPane.showInputDialog(
                    null, menu, catFav.getId(), JOptionPane.INFORMATION_MESSAGE,
                    backgroundCat, buttons, buttons[0]
                );
                int selected = -1;

                for (int i = 0; i < buttons.length; i++) {
                    if (option.equals(buttons[i])) {
                        selected = i;
                    }
                }

                switch(selected) {
                    case 0:
                        getFavRandomPic(api_key, client);
                        break;
                    case 1:
                        deleteFav(api_key, client, catFav.getId());
                        break;
                    case 2:
                        break;
                    default:
                        break;
                }

            } catch (IOException e) {
                System.out.println("Good bye");
            }
        }
    }

    public static void deleteFav(String api_key, OkHttpClient client, String id) throws IOException {
        String url = URL + "/favourites/" + id;
        Request request = new Request.Builder().url(url).method(
                "DELETE", null).addHeader("x-api-key", api_key).build();
        Response response = client.newCall(request).execute();

    }

}