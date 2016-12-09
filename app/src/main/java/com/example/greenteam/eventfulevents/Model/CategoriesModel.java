package com.example.greenteam.eventfulevents.Model;

import java.util.List;

/**
 * Created by NB on 2016-11-24.
 */
public class CategoriesModel {

    public List<Category> category;

    public class Category {
        public String name;
        public String eventCount;
        public String id;
    }
}