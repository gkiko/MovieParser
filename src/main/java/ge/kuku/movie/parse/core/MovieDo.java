package ge.kuku.movie.parse.core;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MovieDo {

    @NotNull
    private String name;

    private String imdbId;
    private String source;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
