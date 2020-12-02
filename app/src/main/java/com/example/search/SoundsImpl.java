package com.example.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SoundsImpl implements Sounds {

    private static final String TAG = "SoundsImpl";
    // natural classes
    private ArrayList VOWELS_GLIDES = new ArrayList<>(Arrays.asList("i","ɪ","u","ʊ","e","ɛ","o","ɔ","a","y","j","w"));
    private ArrayList Y_HIGH = new ArrayList<>(Arrays.asList("i","ɪ","u","ʊ","y","j","w"));
    private ArrayList N_HIGH = subtractList(VOWELS_GLIDES, Y_HIGH);
    private ArrayList Y_LOW = new ArrayList<>(Arrays.asList("a"));
    private ArrayList N_LOW = subtractList(VOWELS_GLIDES, Y_LOW);
    private ArrayList Y_BACK = new ArrayList<>(Arrays.asList("u","ʊ","o","ɔ","a","w"));
    private ArrayList N_BACK = subtractList(VOWELS_GLIDES, Y_BACK);
    private ArrayList Y_ROUND = new ArrayList<>(Arrays.asList("u","ʊ","o","ɔ","w"));
    private ArrayList N_ROUND = subtractList(VOWELS_GLIDES, Y_ROUND);
    private ArrayList Y_ATR = new ArrayList<>(Arrays.asList("i","u","e","o"));
    private ArrayList N_ATR = new ArrayList<>(Arrays.asList("ɪ","ʊ","ɛ","ɔ"));

    private ArrayList<String> CONSONANTS = new ArrayList<>(Arrays.asList(
            "p","b","t","d","k","g","ʔ",
            "m","n","ŋ","ɳ","r",
            "ɸ","β","f","v","θ","ð","s","z","ʃ","ʒ","ç","ʝ","h",
            "ts","dz","tʃ","dʒ",
            "ɹ","l"));
    private ArrayList<String> nasals = new ArrayList<>(Arrays.asList("m","n","ŋ","ɳ"));
    private ArrayList<String> oralStops = new ArrayList<>(Arrays.asList("p","b","t","d","k","g","ʔ"));
    private ArrayList<String> voiced = (ArrayList<String>) mergeLists(new ArrayList<>(Arrays.asList("b","d","g","m","n","ŋ","r","β","v","ð","z","ʒ","dz","dʒ","l","w","j","ɹ")),VOWELS_GLIDES);
    private ArrayList<String> voiceless = new ArrayList<>(Arrays.asList("p","t","k","ʔ","ɸ","f","θ","s","ʃ","ts","tʃ"));
    private ArrayList<String> alveolarFricatives = new ArrayList<>(Arrays.asList("s","z"));
    private ArrayList<String> alveopalatalFricatives = new ArrayList<>(Arrays.asList("ʃ","ʒ"));
    private ArrayList<String> fricatives = new ArrayList<>(Arrays.asList( "f","v","θ","ð","s","z","ʃ","ʒ","ç","ʝ","h"));
    private ArrayList<String> affricates = new ArrayList<>(Arrays.asList("ts","dz","tʃ","dʒ"));
    private ArrayList<String> liquids = new ArrayList<>(Arrays.asList("l","ɹ"));
    private ArrayList<String> laterals = new ArrayList<>(Arrays.asList("l"));
    private ArrayList<String> LABIALS = new ArrayList<>(Arrays.asList("p","b","m","ɸ","β","f","v","w"));

    private ArrayList<String> dentals = new ArrayList<>(Arrays.asList("t","d","n","r","θ","ð","ɹ","l"));
    private ArrayList<String> alveolars = new ArrayList<>(Arrays.asList("t","d","n","r","s","z","ts","dz","ɹ","l"));
    private ArrayList<String> postalveolars = new ArrayList<>(Arrays.asList("t","d","n","r","ʃ","ʒ","tʃ","dʒ","ɹ","l"));
    private ArrayList<String> retroflexes = new ArrayList<>(Arrays.asList("ɳ"));
    private ArrayList<String> palatals = new ArrayList<>(Arrays.asList("j","ç","ʝ"));
    private ArrayList<String> velars = new ArrayList<>(Arrays.asList("k","g","ŋ","w"));
    private ArrayList<String> uvulars = new ArrayList<>();

    private ArrayList<String> y_anterior = (ArrayList<String>)mergeLists(dentals,alveolars);
    private ArrayList<String> n_anterior = (ArrayList<String>)mergeLists(postalveolars,retroflexes);
    private ArrayList<String> CORONALS = (ArrayList<String>)mergeLists(dentals,alveolars,postalveolars,retroflexes);
    private ArrayList<String> DORSALS = (ArrayList<String>)mergeLists(velars,uvulars,palatals);
    private ArrayList<String> GLOTTALS = new ArrayList<>(Arrays.asList("ʔ","h"));
    private ArrayList<String> sonorants = (ArrayList<String>)mergeLists(nasals,liquids,VOWELS_GLIDES);
    private ArrayList<String> stopsAndAffricates  = (ArrayList<String>)mergeLists(oralStops,nasals,affricates);
    private ArrayList<String> stridents = (ArrayList<String>)mergeLists(alveolarFricatives,alveopalatalFricatives,affricates);

    ArrayList Y_CONS = CONSONANTS;
    ArrayList N_CONS = null;
    ArrayList Y_SON = sonorants;
    ArrayList N_SON = null;
    ArrayList Y_VOI = voiced;
    ArrayList N_VOI = null;
    ArrayList Y_CONT = null;
    ArrayList N_CONT = stopsAndAffricates;
    ArrayList Y_NAS = nasals;
    ArrayList N_NAS = null;
    ArrayList Y_STRID = stridents;
    ArrayList N_STRID = null;
    ArrayList Y_LAT = laterals;
    ArrayList N_LAT = null;
    ArrayList LAB = LABIALS;
    ArrayList COR = CORONALS;
    ArrayList DORS = DORSALS;
    ArrayList GLOT = GLOTTALS;
    ArrayList Y_ANT = y_anterior;
    ArrayList N_ANT = n_anterior;


    // features
    String sound;

    String high = ""; String low = "";
    String back = ""; String round = "";
    String atr = "";

    String cons = ""; String son = "";
    String voi = ""; String cont = "";
    String nas = ""; String strid = "";
    String lat = ""; String lab = "";
    String cor = ""; String dors = "";
    String glot = ""; String ant = "";

    public SoundsImpl(String sound) {
        this.sound = sound;

        determineHeight();
        determineLowness();
        determineBackness();
        determineRoundness();
        determineATR();
        determineCons();
        determineSon();
        determineVoi();
        determineCont();
        determineNas();
        determineStrid();
        determineLat();
        determineLAB();
        determineCOR();
        determineDORS();
        determineAnt();
        determineGLOT();
    }

    @Override
    public void setSound(String sound) {
        this.sound = sound;
    }

    @Override
    public String getSound() {
        return this.sound;
    }

    private List<String> mergeLists(ArrayList<String>... list){
        ArrayList<String> list_combined = new ArrayList<String>();
        for (ArrayList<String> inputList: list){
            list_combined.addAll(inputList);
        }
        return list_combined;
    }

    private ArrayList<String> subtractList(ArrayList<String> x, ArrayList<String> y) {
        ArrayList<String> temp = (ArrayList<String>) x.clone();
        for (int i = 0; i < y.size(); i++) {
            if (temp.contains(y.get(i))) {
                temp.remove(y.get(i));
            }
        }
        return temp;
    }

    // Determine features of VOWELS
    public void determineHeight(){
        if (Y_HIGH.contains(this.sound)) {
            this.high = "[+high]";
        } else if (N_HIGH.contains(this.sound)) {
            this.high = "[-high]";
        }
    }

    public void determineLowness(){
        if (Y_LOW.contains(this.sound)) {
            this.low = "[+low]";
        } else if (N_LOW.contains(this.sound)) {
            this.low = "[-low]";
        }
    }

    public void determineBackness(){
        if (Y_BACK.contains(this.sound)) {
            this.back = "[+back]";
        } else if (N_BACK.contains(this.sound)) {
            this.back = "[-back]";
        }
    }

    public void determineRoundness(){
        if (Y_ROUND.contains(this.sound)) {
            this.round = "[+round]";
        } else if (N_ROUND.contains(this.sound)) {
            this.round = "[-round]";
        }

    }

    // [+-ATR] may not be active in many languages with small vowel systems
    public void determineATR(){
        if (Y_ATR.contains(this.sound)) {
            this.atr = "[+ATR]";
        } else if (N_ATR.contains(this.sound)) {
            this.atr = "[-ATR]";
        }
    }

    public void determineCons(){
        if (Y_CONS.contains(this.sound)) {
            this.cons = "[+consonantal]";
        } else {
            this.cons = "[-consonantal]";
        }
    }

    public void determineSon(){
        if (Y_SON.contains(this.sound)) {
            this.son = "[+sonorant]";
        } else {
            this.son = "[-sonorant]";
        }
    }

    public void determineVoi(){
        if (Y_VOI.contains(this.sound)) {
            this.voi = "[+voice]";
        } else {
            this.voi = "[-voice]";
        }
    }

    public void determineCont(){
        if (N_CONT.contains(this.sound)) {
            this.cont = "[-continuant]";
        } else {
            this.cont = "[+continuant]";
        }
    }

    public void determineNas(){
        if (Y_NAS.contains(this.sound)) {
            this.nas = "[+nasal]";
        } else {
            this.nas = "[-nasal]";
        }
    }

    public void determineStrid(){
        if (Y_STRID.contains(this.sound)) {
            this.strid = "[+strident]";
        } else {
            this.strid = "[-strident]";
        }
    }

    public void determineLat(){
        if (Y_LAT.contains(this.sound)) {
            this.lat = "[+lateral]";
        } else {
            this.lat = "[-lateral]";
        }
    }

    public void determineLAB(){
        if (LAB.contains(this.sound)) {
            this.lab = "[LABIAL]";
        }
    }

    public void determineCOR(){
        if (COR.contains(this.sound)) {
            this.cor = "[CORONAL]";
        }
    }

    public void determineAnt(){
        if (Y_ANT.contains(this.sound)) {
            this.ant = "[+anterior]";
        } else if (N_ANT.contains(this.sound)){
            this.ant = "[-anterior]";
        }
    }

    public void determineDORS(){
        if (DORS.contains(this.sound)) {
            this.dors = "[DORSAL]";
        }
    }

    public void determineGLOT(){
        if (GLOT.contains(this.sound)) {
            this.glot = "[GLOTTAL]";
        }
    }
}
