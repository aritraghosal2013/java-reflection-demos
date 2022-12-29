package org.example.models;

public class Item {

    private Integer id;
    private String name;
    private Float price;

    public Item() {
    }

    public Item(Integer id, String name, Float price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public void displayItem() {
        System.out.println("Item is being displayed");
        System.out.println("Id: " + id);
        System.out.println("Name: " + name);
        System.out.println("Price: " + price);
    }

    private void displayPrivateInfo(String callerMethod) {
        System.out.println("This is a call to a private method " + callerMethod);
    }
}
