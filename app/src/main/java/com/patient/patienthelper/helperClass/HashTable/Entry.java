package com.patient.patienthelper.helperClass.HashTable;

public class Entry<K, V> {
    private K kay;
    private V value;

    public Entry(K kay, V value) {
        this.kay = kay;
        this.value = value;
    }

    public K getKay() {
        return kay;
    }

    public void setKay(K kay) {
        this.kay = kay;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }
}
