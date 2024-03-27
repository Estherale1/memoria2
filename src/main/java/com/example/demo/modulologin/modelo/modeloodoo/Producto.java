package com.example.demo.modulologin.modelo.modeloodoo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Producto {
    
    private int id;
    private String name;
    private double list_price;
    private String categ_id;
    private String description_sale;
    private String barcode;
    private double stock;
    private String image_1024;

    public Producto(int id, String name, double list_price,String categ_id, String description_sale, String barcode, String image_1024) {
        this.id = id;
        this.name = name;
        this.list_price = list_price;
        this.categ_id = categ_id;
        this.description_sale = description_sale;
        this.barcode = barcode;
        this.image_1024 = image_1024;
    }
}

