import ge.kuku.movie.parse.data.OmdbClient;

import java.util.Map;

class FakeOmdbClient extends OmdbClient {

    private static FakeOmdbClient client;
    private String nameToReuturn;

    static FakeOmdbClient instance() {
        if (client == null) {
            client = new FakeOmdbClient();
        }
        return client;
    }

    @Override
    public String getMovieNameById(String id) {
        return nameToReuturn;
    }

    public void setNameToReturn(String nameToReturn) {
        this.nameToReuturn = nameToReturn;
    }
}
