package com.sda.david.fanmovieapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by david on 29/04/2017.
 */

public class User implements Parcelable {

    private Long id;

    private String name;

    @SerializedName("user_name")
    private String userName;

    private String password;

    @SerializedName("favorites_movies")
    private List<Long> favoritesMovies;

    private boolean administrator;

    public User() {
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public User(String name, String userName, String password, List<Long> favoritesMovies, boolean administrator) {
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.favoritesMovies = favoritesMovies;
        this.administrator = administrator;
    }

    public User(Long id, String name, String userName, List<Long> favoritesMovies, boolean administrator) {
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.favoritesMovies = favoritesMovies;
        this.administrator = administrator;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

//    public List<Long> getFavoritesMovies() {
//        return favoritesMovies;
//    }

    public boolean isAdministrator() {
        return administrator;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeString(this.userName);
        dest.writeString(this.password);
        dest.writeList(this.favoritesMovies);
        dest.writeByte(this.administrator ? (byte) 1 : (byte) 0);
    }

    protected User(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.name = in.readString();
        this.userName = in.readString();
        this.password = in.readString();
        this.favoritesMovies = new ArrayList<Long>();
        in.readList(this.favoritesMovies, Long.class.getClassLoader());
        this.administrator = in.readByte() != 0;
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
