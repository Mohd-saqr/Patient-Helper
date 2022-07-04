package com.patient.patienthelper.helperClass.HashTable;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.patient.patienthelper.api.Disease;

import java.util.*;


/*
this implementation  in chaining algorithm we can use the open addressing also.
 */

public class HashTable<K, V> {
    private int bound; // the bound of the Hash table(how many buckets you need)
    private int size;
    private final LinkedList<Entry<String, Disease>>[] entries;

    // default  constructor with bound
    public HashTable(int bound) {
        this.bound = bound;
        entries = new LinkedList[bound];
    }


    public void put(String kay, Disease value) {
        int index = hashedCode(kay);
        /*
        if the buckets in this index is null
        */
//        LinkedList<Entry<K, V>> bucket1 = ;
        if (entries[index] == null) {
            entries[index] = new LinkedList<>();
        }
//        LinkedList<Entry<K, V>> bucket = ;
        for (Entry<String, Disease> entry : entries[index]) {
            if (entry.getKay() == kay) {
                entry.setValue(value);
                return;
            }

        }
        entries[index].addFirst(new Entry<>(kay, value)); // we use add first because it faster than add last
        size++;

    }

    public V get(String kay) {
        /// get the index
        int index = hashedCode(kay);
        // initialise the buckets;
        LinkedList<Entry<String, Disease>> bucket = entries[index];
        // if the hash table is empty
//        throw new IllegalArgumentException("Empty HashTable");
        if (bucket == null) return null;
        for (Entry entry : bucket) {
            if (entry.getKay().equals(kay))
                return (V) entry.getValue();
        }

        // if we not found the key
//        throw new IllegalArgumentException("the kay not found");
        return null;
    }

    public boolean contains(String kay) {
        int index = hashedCode(kay);
        LinkedList<Entry<String, Disease>> bucket = entries[index];
        if (bucket == null) return false;
        for (Entry<String, Disease> entry : bucket) {
            if (Objects.equals(entry.getKay(), kay)) return true;
        }
        return false;
    }

    public void remove(String kay) {
        int index = hashedCode(kay);
        LinkedList<Entry<String, Disease>> bucket = entries[index];
        if (bucket == null) throw new IllegalArgumentException("Key Not Found");
        for (Entry<String, Disease> entry : bucket) {
            if (entry.getKay() == kay) {
                bucket.remove(entry);
                size--;
            } else throw new IllegalArgumentException("Key Not Found");
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public Collection<String> keys() {
        Collection<String> keys = new ArrayList<>();
        Arrays.stream(entries).forEach(LL -> {
            if (LL != null) {
                LL.stream().forEach(entry -> {
                    keys.add(entry.getKay());
                });
            }
        });


        return keys;
    }

    public int hash(String key) {
        /// if the key in found return the index of collection for that key if not return 0
        return (contains(key)) ? Math.abs(key.hashCode() % bound) : 0;
    }


    private int hashedCode(String key) {

        return (Objects.hashCode(key) < 0) ? Objects.hashCode(key) * -1 % (bound) : Objects.hashCode(key) % (bound);
    }


    public int getSize() {
        return size;
    }
}
