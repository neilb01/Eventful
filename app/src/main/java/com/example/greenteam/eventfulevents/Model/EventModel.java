package com.example.greenteam.eventfulevents.Model;

import java.util.List;

/**
 * Created by NB on 2016-11-19.
 */

public class EventModel {

    public Events events;

    public class Events {

        public List<Event> event;

        public class Event {

            public String title;
            public String description;
            public String start_time;
            public String venue_name;
            public Images image;

            public class Images {
                public Medium medium;

                public class Medium {
                    public String url;
                }
            }
        }
    }
}
