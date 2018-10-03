package com.dicoding.mymoviecataloguev2;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class MovieItem implements Parcelable {
    private String judul, overview, releaseDate, moviePoster;
    private double vote,voteCount;
    private double id;

    public MovieItem(JSONObject object) {
        try {
            double id = object.getDouble("id");
            String judul = object.getString("title");
            String overview = object.getString("overview");
            String releaseDate = object.getString("release_date");
            String moviePoster = object.getString("poster_path");
            double vote = object.getDouble("vote_average");
            double voteCount = object.getDouble("vote_count");

            this.id = id;
            this.judul = judul;
            this.overview = overview;
            this.releaseDate = releaseDate;
            this.moviePoster = moviePoster;
            this.vote = vote;
            this.voteCount = voteCount;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public MovieItem(){

    }

    protected MovieItem(Parcel in) {
        judul = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
        moviePoster = in.readString();
        vote = in.readDouble();
        voteCount = in.readDouble();
        id = in.readDouble();
    }

    public static final Creator<MovieItem> CREATOR = new Creator<MovieItem>() {
        @Override
        public MovieItem createFromParcel(Parcel in) {
            return new MovieItem(in);
        }

        @Override
        public MovieItem[] newArray(int size) {
            return new MovieItem[size];
        }
    };

    public String getPoster() {
        return moviePoster;
    }

    public void setPoster(String ivPoster) {
        this.moviePoster = ivPoster;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public double getVote() {
        return vote;
    }

    public void setVote(double vote) {
        this.vote = vote;
    }
    public void setVote(String vote) {
        this.vote = Double.valueOf(vote);
    }

    public double getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(double voteCount) {
        this.voteCount = voteCount;
    }
    public void setVoteCount(String voteCount) {
        this.vote = Double.valueOf(voteCount);
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(judul);
        dest.writeString(overview);
        dest.writeString(releaseDate);
        dest.writeString(moviePoster);
        dest.writeDouble(vote);
        dest.writeDouble(voteCount);
        dest.writeDouble(id);
    }
}
