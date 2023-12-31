package com.example.Dist_sys_lab1_webshop.Model.User;

import com.example.Dist_sys_lab1_webshop.Model.Item.Item;

import java.util.ArrayList;

public class ShoppingCart {
    private ArrayList<ShoppingItem> items;

    public ShoppingCart() {
        this.items = new ArrayList<>();
    }

    public ArrayList<ShoppingItem> getItems() {
        return items;
    }

    public void emptyCart(){
        items.clear();
    }

    public void addItems(Item item, int nrOfItems) {
        for(int i =0; i<items.size(); i++){
            if(items.get(i).getItem().getId()==item.getId()){ //if item already exist, just increment the nrofitems of that item
                items.get(i).addNrOfItems(nrOfItems);
                return;
            }
        }
        System.out.println("item not found");
        items.add(new ShoppingItem(item, nrOfItems));

    }
    public void removeItems(Item item, int nrOfItemsToRemove) {
        for(int i =0; i<items.size(); i++){
            if(items.get(i).getItem().getId()==item.getId()){ //if item already exist, just increment the nrofitems of that item
                int newNrOfItems = items.get(i).getNrOfItems() - nrOfItemsToRemove;
                if(newNrOfItems<0) newNrOfItems=0;
                items.set(i,new ShoppingItem(item,newNrOfItems));
                return;
            }
        }
    }

    public int getQuantityFromItemId(int itemId) {
        for (ShoppingItem item : items) {
            if (item.getItem().getId() == itemId) {

                return item.getNrOfItems();
            }
        }
        return 0;
    }



    public double getTotalPrice(){
        double total = 0;

        for (ShoppingItem item : items) {
            total += item.getItem().getPrice() * item.getNrOfItems();
        }

        return total;
    }

    public static ShoppingCart getCopy(ShoppingCart cart) {
        ArrayList<ShoppingItem> shoppingItems = cart.getItems();
        ShoppingCart cartCopy = new ShoppingCart();
        for (ShoppingItem item : shoppingItems) {
            ShoppingItem cpy = ShoppingItem.getCopy(item);
            cartCopy.items.add(cpy);
        }
        return cartCopy;
    }

    @Override
    public String toString() {
        return "Shoppingcart: " + items;
    }
}
