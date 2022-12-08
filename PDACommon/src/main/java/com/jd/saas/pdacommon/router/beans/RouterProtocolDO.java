package com.jd.saas.pdacommon.router.beans;

/**
 * @author majiheng
 */
public class RouterProtocolDO {
    private String scheme;
    private String host;
    private String path;
    private String parmas;

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getParmas() {
        return parmas;
    }

    public void setParmas(String parmas) {
        this.parmas = parmas;
    }
}
