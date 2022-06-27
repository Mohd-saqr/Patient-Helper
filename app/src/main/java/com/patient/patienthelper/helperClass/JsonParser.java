package com.patient.patienthelper.helperClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JsonParser {

    private HashMap<String,String> parserJsonObject(JSONObject object){

        //initialize hash map
        HashMap<String,String> dataList = new HashMap<>();
        try {
            //get name from object
            String name = object.getString("name");
            //get latitude from object
            String latitude = object.getJSONObject("geometry").getJSONObject("location").getString("lat");
            //get longitude from object
            String longitude = object.getJSONObject("geometry").getJSONObject("location").getString("lng");
            //put all value in hash map
            dataList.put("name",name);
            dataList.put("lat",latitude);
            dataList.put("lng",longitude);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //return hashmap
        assert dataList!=null;
        return dataList;
    }

    private List<HashMap<String,String>> parseJsonArray(JSONArray jsonArray){
        //initialize hashmap list
        List<HashMap<String,String>> dataList = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                //initialize hashmap
                HashMap<String,String> data = parserJsonObject(((JSONObject) jsonArray.get(i) ));
                //add data in hashmap list
                dataList.add(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //return hashmap list
        return dataList;
    }

    public List<HashMap<String,String>> parseResult(JSONObject object){
        //initialize json array
        JSONArray jsonArray = null;

        try {
            //get result array
            jsonArray = object.getJSONArray("results");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //return array
        return parseJsonArray(jsonArray);
    }

    ////////////////////////////////////////////|||||||||\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

    private HashMap<String,String> parserJsonObjectToSave(JSONObject object){

        //initialize hash map
        HashMap<String,String> dataList = new HashMap<>();
        try {
            //get name from object
            String name = object.getString("name");
            //get latitude from object
            String latitude = object.getJSONObject("geometry").getJSONObject("location").getString("lat");
            //get longitude from object
            String longitude = object.getJSONObject("geometry").getJSONObject("location").getString("lng");
            String isOpen = object.getJSONObject("opening_hours").getBoolean("open_now")+"";
            String rating = object.getDouble("rating")+"";
            //put all value in hash map
            dataList.put("name",name);
            dataList.put("lat",latitude);
            dataList.put("lng",longitude);
            dataList.put("open_now",isOpen);
            dataList.put("rating",rating);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        //return hashmap
        assert dataList!=null;
        return dataList;
    }

    private List<HashMap<String,String>> parseJsonArrayToSave(JSONArray jsonArray){
        //initialize hashmap list
        List<HashMap<String,String>> dataList = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                //initialize hashmap
                HashMap<String,String> data = parserJsonObjectToSave(((JSONObject) jsonArray.get(i) ));
                //add data in hashmap list
                dataList.add(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //return hashmap list
        return dataList;
    }

    public List<HashMap<String,String>> parseResultToSave(JSONObject object){
        //initialize json array
        JSONArray jsonArray = null;

        try {
            //get result array
            jsonArray = object.getJSONArray("results");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //return array
        return parseJsonArrayToSave(jsonArray);
    }
}
