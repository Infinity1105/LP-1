import java.util.*;
import java.io.*;


class MyException extends Exception{
    void lessPP(){
        System.out.println("Less Positional Parametes");
    }
}

public class MacroPass2 {

    int kp,pp,mdtp,kpdtp;
    String name;
    HashMap<String,MNTEntry> mntTab;
    HashMap<String,Integer>apTab;
    HashMap<Integer,String>apInverse;
    ArrayList<String>mdtTab;
    ArrayList<String>kpdTab;
    MacroPass2  ()
    {
    kp=0;pp=0;mdtp=0;kpdtp=0;
    mntTab=new HashMap<>();
    apTab=new HashMap<>();
    apInverse=new HashMap<>();
    mdtTab=new ArrayList<>();
    kpdTab=new ArrayList<>();
    }


    void makeMNT() throws Exception{
        BufferedReader mnt=new BufferedReader(new FileReader("MNT.txt"));
        String Line="";
        while((Line=mnt.readLine())!=null){
            String parts[]=Line.split("\\s+");
            String name1=parts[0];
            int p1=Integer.parseInt(parts[1]);
            int k1=Integer.parseInt(parts[2]);
            int m1=Integer.parseInt(parts[3]);
            int kp1=Integer.parseInt(parts[4]);
            mntTab.put(name1,new MNTEntry(name1,p1,k1,m1,kp1));
        }
        mnt.close();
    }
    void makeKPTab() throws Exception{
        BufferedReader kpTab=new BufferedReader(new FileReader("kpTab.txt"));
        String Line;
        while((Line=kpTab.readLine())!=null){
            kpdTab.add(Line);
        }
        kpTab.close();
    }
    void makeMDT() throws Exception{
        BufferedReader MDTTab=new BufferedReader(new FileReader("MDT.txt"));
        String Line;
        while((Line=MDTTab.readLine())!=null){
            mdtTab.add(Line);
        }
        MDTTab.close();
    }
    void MakePass2() throws Exception{
        makeKPTab();
        makeMNT();
        makeMDT();
        String Line="";
        BufferedReader br=new BufferedReader(new FileReader("IC.txt"));
        BufferedWriter bw=new BufferedWriter(new FileWriter("Pass2.txt"));
        while((Line=br.readLine())!=null){
            String [] parts=Line.split("\\s+");
            if(mntTab.containsKey(parts[0])){
                pp=mntTab.get(parts[0]).getPp();
                kp=mntTab.get(parts[0]).getPp();
                kpdtp=mntTab.get(parts[0]).getKpdtp();
                mdtp=mntTab.get(parts[0]).getMdtp();
                int ParamNo=1;
                for(int i=0;i<pp;i++){
                    parts[ParamNo]=parts[ParamNo].replace(",","");
                    apTab.put(parts[ParamNo],ParamNo );
                    apInverse.put(ParamNo,parts[ParamNo]);
                    ParamNo++;
                }
                int j=kpdtp-1;
                for(int i=0;i<kp;i++){
                    String splits[]=kpdTab.get(j).split("\t");
                    apTab.put(splits[0],ParamNo );
                    apInverse.put(ParamNo,splits[1]);
                    ParamNo++;
                    j++;
                }
                for(int i=pp+1;i< parts.length;i++){
                    parts[i]=parts[i].replace(",","");
                    String splits[]=parts[i].split("=");
                    String name=splits[0].replace("&","");
                    int tempP=apTab.get(name);
                    apInverse.put(tempP,splits[1]);
                }
                int i=mdtp-1;
                String code;
                while(!mdtTab.get(i).equalsIgnoreCase("MEND")){
                    code="";
                    System.out.println(mdtTab.get(i));
                    String splits[]=mdtTab.get(i).split("\\s+");
                    for(int p=0;p<splits.length;p++){
                        if(splits[p].contains("(P,")){

                            splits[p]=splits[p].replace("(","")
                                    .replace(")","");
                            System.out.println(splits[p]);
                            String child[]=splits[p].split(",");
                            code=apInverse.get(Integer.parseInt(child[1]));
                            bw.write(code+"\t");
                        }else{
                            System.out.println(splits[p]);
                            bw.write(splits[p]+"\t");
                        }
                    }
                    bw.write("\n");
                    i++;
                }

            }
            else{
                bw.write(Line+"\n");
            }
        }
        bw.close();
        br.close();
    }
    public static void main(String[] args) {
            try{
                MacroPass2 passTwo=new MacroPass2();
                passTwo.MakePass2();
            }catch (Exception e){
                e.printStackTrace();
            }
    }
}
