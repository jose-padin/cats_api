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
    Dotenv dotenv = Dotenv.load();
    int id;
// TODO: store the api key in a safe place
//    String api_key = "82c59816-cbce-4c7b-8fbf-9308b701ae5d";
    String api_key = dotenv.get("CAT_API_KEY");
    String image;
    String url;
}
