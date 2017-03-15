package szp.rafael.swarmexample;

/**
 * Created by rafaelszp on 9/21/16.
 */
public class HTTPSCertificate {

    private String keystorePath;
    private String password;
    private String alias;

    public HTTPSCertificate(String keystorePath, String password, String alias) {
        this.keystorePath = keystorePath;
        this.password = password;
        this.alias = alias;
    }

    public String getKeystorePath() {
        return keystorePath;
    }

    public void setKeystorePath(String keystorePath) {
        this.keystorePath = keystorePath;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
