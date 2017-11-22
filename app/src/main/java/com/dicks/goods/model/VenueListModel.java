package com.dicks.goods.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;



public class VenueListModel{
    @SerializedName("venues")
    private List<Venues> venues;

    public VenueListModel() {
    }

    public List<Venues> getVenues() {
        return venues;
    }

    public void setVenues(List<Venues> venues) {
        this.venues = venues;
    }

    public static class Location{
        @SerializedName("address")
        private String address;
        @SerializedName("latitude")
        private double latitude;
        @SerializedName("longitude")
        private double longitude;
        @SerializedName("postalCode")
        private String postalcode;
        @SerializedName("cc")
        private String cc;
        @SerializedName("city")
        private String city;
        @SerializedName("state")
        private String state;
        @SerializedName("country")
        private String country;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public String getPostalcode() {
            return postalcode;
        }

        public void setPostalcode(String postalcode) {
            this.postalcode = postalcode;
        }

        public String getCc() {
            return cc;
        }

        public void setCc(String cc) {
            this.cc = cc;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }
    }

    public static class Contacts{
        @SerializedName("phone")
        private String phone;
        @SerializedName("twitter")
        private String twitter;
        @SerializedName("facebook")
        private String facebook;
        @SerializedName("facebookName")
        private String facebookname;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getTwitter() {
            return twitter;
        }

        public void setTwitter(String twitter) {
            this.twitter = twitter;
        }

        public String getFacebook() {
            return facebook;
        }

        public void setFacebook(String facebook) {
            this.facebook = facebook;
        }

        public String getFacebookname() {
            return facebookname;
        }

        public void setFacebookname(String facebookname) {
            this.facebookname = facebookname;
        }
    }

    public static class Photos{
        @SerializedName("photoId")
        private String photoid;
        @SerializedName("createdAt")
        private int createdat;
        @SerializedName("url")
        private String url;

        public String getPhotoid() {
            return photoid;
        }

        public void setPhotoid(String photoid) {
            this.photoid = photoid;
        }

        public int getCreatedat() {
            return createdat;
        }

        public void setCreatedat(int createdat) {
            this.createdat = createdat;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class Venues{
        @SerializedName("id")
        private String id;
        @SerializedName("name")
        private String name;
        @SerializedName("verified")
        private boolean verified;
        @SerializedName("url")
        private String url;
        @SerializedName("ratingColor")
        private String ratingcolor;
        @SerializedName("ratingSignals")
        private int ratingsignals;
        @SerializedName("rating")
        private double rating;
        @SerializedName("storeId")
        private String storeid;
        @SerializedName("location")
        private Location location;
        @SerializedName("contacts")
        private List<Contacts> contacts;
        @SerializedName("photos")
        private List<Photos> photos;
        private float distance;
        private boolean favourite;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Location getAddress(){
            return this.location;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean getVerified() {
            return verified;
        }

        public void setVerified(boolean verified) {
            this.verified = verified;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getRatingcolor() {
            return ratingcolor;
        }

        public void setRatingcolor(String ratingcolor) {
            this.ratingcolor = ratingcolor;
        }

        public int getRatingsignals() {
            return ratingsignals;
        }

        public void setRatingsignals(int ratingsignals) {
            this.ratingsignals = ratingsignals;
        }

        public double getRating() {
            return rating;
        }

        public void setRating(double rating) {
            this.rating = rating;
        }

        public String getStoreid() {
            return storeid;
        }

        public void setStoreid(String storeid) {
            this.storeid = storeid;
        }

        public android.location.Location getLocation() {
            android.location.Location location = new android.location.Location("server");
            location.setLatitude(this.location.getLatitude());
            location.setLatitude(this.location.getLongitude());
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public List<Contacts> getContacts() {
            return contacts;
        }

        public void setContacts(List<Contacts> contacts) {
            this.contacts = contacts;
        }

        public List<Photos> getPhotos() {
            return photos;
        }

        public void setPhotos(List<Photos> photos) {
            this.photos = photos;
        }

        public void setDistance(float distance) {
            this.distance = distance;
        }

        public float getDistance() {
            return distance;
        }

        public boolean isFavourite() {
            return favourite;
        }

        public void setFavourite(boolean favourite) {
            this.favourite = favourite;
        }
    }
}
