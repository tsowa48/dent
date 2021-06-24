package gcg.word.util;

public class Rsid {
    private String R;
    private String RPr;
    private String RDefault;
    private String P;
    private String Tr;

    public Rsid(String R, String RPr, String RDefault, String P, String Tr) {
        this.R = R;
        this.RPr = RPr;
        this.RDefault = RDefault;
        this.P = P;
        this.Tr = Tr;
    }

    public String rsidR() {
        return R;
    }

    public String rsidRPr() {
        return RPr;
    }

    public String rsidRDefault() {
        return RDefault;
    }

    public String rsidP() {
        return P;
    }

    public String rsidTr() {
        return Tr;
    }
}
