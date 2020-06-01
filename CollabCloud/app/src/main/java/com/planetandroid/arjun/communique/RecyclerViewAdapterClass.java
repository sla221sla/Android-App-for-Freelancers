package com.planetandroid.arjun.communique;

public class RecyclerViewAdapterClass {


        private String name, hrs, perc;

        public RecyclerViewAdapterClass() {
        }

        public RecyclerViewAdapterClass(String title, String hours, String percen) {
            this.name = title;
            this.hrs = hours;
            this.perc = percen;
        }

        public String getTitle() {
            return name;
        }

        public void setTitle(String name) {
            this.name = name;
        }

        public String getHours() {
            return hrs;
        }

        public void setYear(String year) {
            this.hrs = year;
        }

        public String getPercentage() {
            return perc;
        }

        public void setGenre(String genre) {
            this.perc = genre;
        }
}

