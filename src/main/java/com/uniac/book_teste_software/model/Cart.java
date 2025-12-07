package com.uniac.book_teste_software.model;


import java.util.HashMap;
import java.util.Map;


public class Cart {
    private Map<Long,Integer> items = new HashMap<>(); // bookId -> qty


    public void add(Long bookId, int qty){ items.put(bookId, items.getOrDefault(bookId,0)+qty); }
    public void remove(Long bookId){ items.remove(bookId); }
    public Map<Long,Integer> getItems(){ return items; }
}