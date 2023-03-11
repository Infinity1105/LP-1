public class MNTEntry {

    String name;
    int pp,kp,mdtp,kpdtp;
    MNTEntry(String name,int pp,int kp,int mdtp,int kpdtp){
        this.name=name;
        this.pp=pp;
        this.kp=kp;
        this.mdtp=mdtp;
        this.kpdtp=kpdtp;

    }

    public String getName() {
        return name;
    }

    public int getPp() {
        return pp;
    }

    public int getKp() {
        return kp;
    }

    public int getMdtp() {
        return mdtp;
    }

    public int getKpdtp() {
        return kpdtp;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setKpdtp(int kpdtp) {
        this.kpdtp = kpdtp;
    }

    public void setMdtp(int mdtp) {
        this.mdtp = mdtp;
    }

    public void setPp(int pp) {
        this.pp = pp;
    }

    public void setKp(int kp) {
        this.kp = kp;
    }
}
