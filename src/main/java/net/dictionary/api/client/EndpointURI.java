package net.dictionary.api.client;

public enum EndpointURI {
    TRANSLATE("/translate"),
    LOOKUP("/lookup"),
    GET_LANGS("/getLangs");

    String path;

    EndpointURI(String path){
        this.path = path;
    }

    public String getPath(){
        return path;
    }

    public String addPath(String additionalPath) {
        return path + additionalPath;
    }

}
