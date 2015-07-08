package ge.kuku.movie.parse.data;

import ge.kuku.movie.parse.core.MovieDo;

public class ImoviesEntity {
    private String imoviesId;
    private String imdbId;
    private String movieName;
    private String language;
    private String quality;
    private String source;

    public String getImoviesId() {
        return imoviesId;
    }

    public void setImoviesId(String imoviesId) {
        this.imoviesId = imoviesId;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public MovieDo toMovieDo() {
        MovieDo movieDo = new MovieDo();
        movieDo.setSource(source);
        movieDo.setLanguage(language);
        movieDo.setQuality(quality);

        return movieDo;
    }
}
