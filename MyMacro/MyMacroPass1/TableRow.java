public class TableRow {

    String MNanme;
    int npp,nkp,MDT_ptr;

    TableRow(String MNanme,int npp,int nkp,int MDT_ptr){
        this.MNanme=MNanme;
        this.npp=npp;
        this.nkp=nkp;
        this.MDT_ptr=MDT_ptr;
    }

    public void setMDT_ptr(int MDT_ptr) {
        this.MDT_ptr = MDT_ptr;
    }

    public void setMNanme(String MNanme) {
        this.MNanme = MNanme;
    }

    public void setNkp(int nkp) {
        this.nkp = nkp;
    }

    public void setNpp(int npp) {
        this.npp = npp;
    }

    public int getMDT_ptr() {
        return MDT_ptr;
    }

    public int getNkp() {
        return nkp;
    }

    public int getNpp() {
        return npp;
    }

    public String getMNanme() {
        return MNanme;
    }
}
