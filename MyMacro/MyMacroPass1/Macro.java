import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Macro {
    LinkedHashMap<String,LinkedHashMap<String,Integer>> ppTab;
    LinkedHashMap<String,TableRow> MNTab;
    ArrayList<String>MDTab;
    private BufferedReader br;
    int MDTptr;
    int kpdtp;

    Macro(){
        ppTab=new LinkedHashMap<>();
        MDTptr=1;
        MNTab=new LinkedHashMap<>();
        MDTab=new ArrayList<>();
        kpdtp=0;
    }

    void MakePass1() throws Exception {
        BufferedWriter MDT=new BufferedWriter(new FileWriter("MDT.txt"));
        BufferedWriter MNT =new BufferedWriter(new FileWriter(("MNT.txt")));
        BufferedWriter kp=new BufferedWriter(new FileWriter("kpTab.txt"));
        BufferedWriter pn=new BufferedWriter(new FileWriter("pnTab.txt"));
        BufferedWriter IC=new BufferedWriter(new FileWriter("IC.txt"));
        String Line,Code;
        boolean isPrev=false;
        boolean isStart=false;
        br=new BufferedReader(new FileReader("input.asm"));
        String Mname="";
        while ((Line= br.readLine())!=null){
            Code="";
            String [] parts=Line.split("\\s+");

            if(parts[0].equalsIgnoreCase("MACRO"))
            {
                LinkedHashMap<String, Integer> temp = new LinkedHashMap<>();
                isPrev = true;
                Line = br.readLine();
                parts = Line.split("\\s+");
                Mname = parts[0];
                pn.write(Mname+"\t");
                int NumKP = 0, NumPP = 0, TotalP = 0;
                for (int i = 1; i < parts.length; i++) {
                    parts[i]=parts[i].replace("&", "").replace(",","");
                    if (parts[i].contains("=")) {
                        String Splits[] = parts[i].split("\\=");
                        kp.write(Splits[0]+"\t"+Splits[1]+"\n");
                        pn.write(Splits[0]+"\t");
                        temp.put(Splits[0], ++TotalP);
                        NumKP++;
                    } else {
                        temp.put(parts[i], ++TotalP);
                        pn.write(parts[i]+"\t");
                        NumPP++;
                    }
                }
                pn.write("\n");
                ppTab.put(Mname, temp);
                Code = Mname + "\t" + Integer.toString(NumKP) + "\t" + Integer.toString(NumPP) + "\t" +
                        Integer.toString(MDTptr)+"\t"+(NumKP==0?kpdtp:(kpdtp+1));
                kpdtp+=NumKP;
                MNT.write(Code+"\n");
            }else if(parts[0].equalsIgnoreCase("MEND")){
                isPrev=false;
                Mname="";
                Code+="MEND\n";
                MDT.write(Code);
                MDTptr++;
            }
            else if(isPrev){
                LinkedHashMap<String,Integer> temp=new LinkedHashMap<>();
                temp=ppTab.get(Mname);
                for(int i=0;i<parts.length;i++){
                    parts[i]=parts[i].replace("&","").replace(",","");
                    if(temp.containsKey(parts[i])){
                        Code+="(P,"+Integer.toString(temp.get(parts[i]))+")\t";
                    }else{
                        Code+=parts[i]+"\t";
                    }
                }
                Code+="\n";
                MDT.write(Code);
                MDTab.add(Code);
                MDTptr++;
            }else if(parts[0].equalsIgnoreCase("START")){
                IC.write(parts[0]+"\n");
                isStart=true;
            }else if(isStart){
                IC.write(Line+"\n");
            }
        }
//        PrintMDT();
        br.close();
        MNT.close();
        MDT.close();
        kp.close();
        pn.close();
        IC.close();
    }
//    void PrintMDT(){
//        for(int i=0;i<MDTab.size();i++){
//            System.out.print(i+1 +": "+MDTab.get(i)+"\n");
//        }
//    }
    public static void main(String[] args) {
        try {
            Macro macro=new Macro();
            macro.MakePass1();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

