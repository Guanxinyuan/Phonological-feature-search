package com.example.search;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String ETText;
    private static final String TAG = "MainActivity";
    private TextView text_vowel_hi;
    private TextView text_vowel_lo;
    private TextView text_vowel_bk;
    private TextView text_vowel_rd;
    private TextView text_vowel_atr;

    // Vowel classes
    private ArrayList<String> vowels = new ArrayList<>(Arrays.asList("i","ɪ","u","ʊ","e","ɛ","o","ɔ","a","y"));
    private ArrayList<String> consonants = new ArrayList<>(Arrays.asList(
            "p","b","t","d","k","g","ʔ",
            "m","n","ŋ","ɳ","r",
            "ɸ","β","f","v","θ","ð","s","z","ʃ","ʒ","ç","ʝ","h",
            "ts","dz","tʃ","dʒ",
            "ɹ","l"));
    private ArrayList<String> glides = new ArrayList<>(Arrays.asList("j","w"));

    private TextView text_consonant_cons;
    private TextView text_consonant_son;
    private TextView text_consonant_voi;
    private TextView text_consonant_cont;
    private TextView text_consonant_strid;
    private TextView text_consonant_nas;
    private TextView text_consonant_lat;
    private TextView text_consonant_lab;
    private TextView text_consonant_cor;
    private TextView text_consonant_ant;
    private TextView text_consonant_dors;
    private TextView text_consonant_glot;
    private String input = "";
    private ArrayList<SoundsImpl> inputList = new ArrayList<>();

    ArrayAdapter arrayAdapter;
    private SearchView mSearchView;
    private Toolbar mToolBar;
    private MenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        mToolBar.setTitle("Phonological Features Search");
        setSupportActionBar(mToolBar);
        // initialize UIs
        initUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);

        menuItem = menu.findItem(R.id.search_icon);
        mSearchView = (SearchView) menuItem.getActionView();
        mSearchView.setQueryHint("Search Features Here!");
        mSearchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                initData();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

        private void initUI() {
            // textViews of vowels
            text_vowel_hi = findViewById(R.id.tv_vowel_hi);
            text_vowel_lo = findViewById(R.id.tv_vowel_lo);
            text_vowel_bk = findViewById(R.id.tv_vowel_bk);
            text_vowel_rd = findViewById(R.id.tv_vowel_rd);
            text_vowel_atr = findViewById(R.id.tv_vowel_atr);

            // textViews of consonants
            text_consonant_cons = findViewById(R.id.tv_consonant_cons);
            text_consonant_son = findViewById(R.id.tv_consonant_son);
            text_consonant_voi = findViewById(R.id.tv_consonant_voi);
            text_consonant_cont = findViewById(R.id.tv_consonant_cont);
            text_consonant_nas = findViewById(R.id.tv_consonant_nas);
            text_consonant_strid = findViewById(R.id.tv_consonant_strid);
            text_consonant_lat = findViewById(R.id.tv_consonant_lat);
            text_consonant_lab = findViewById(R.id.tv_consonant_lab);
            text_consonant_cor = findViewById(R.id.tv_consonant_cor);
            text_consonant_ant = findViewById(R.id.tv_consonant_ant);
            text_consonant_dors = findViewById(R.id.tv_consonant_dors);
            text_consonant_glot = findViewById(R.id.tv_consonant_glot);

        }

        @Override
        public void onClick(View v) {
            initData();
        }

        private void initData() {
            reset();
            Log.i(TAG,"Already reset");

            mSearchView.clearFocus();
            InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            in.hideSoftInputFromWindow(mSearchView.getWindowToken(), 0);

            ETText = mSearchView.getQuery().toString();

            Log.i(TAG, "输入的国际音标为" + ETText);

            for (int i = 0; i < ETText.length(); i++){
                if (ETText.charAt(i) == ',' || ETText.charAt(i) == ' '){
                    if (input != "") {
                        SoundsImpl soundInfo = new SoundsImpl(input);
                        inputList.add(soundInfo);
                        input = "";
                    }
                } else {
                    input = input + ETText.charAt(i);
                    if (i == ETText.length()-1){
                        inputList.add(new SoundsImpl(input));
                    }
                }
            }

            compareFeatures();
        }

        private void compareFeatures() {
            compareVowelFeatures();
            compareConsonantFeatures();
        }

        private void compareVowelFeatures() {
            String x = "";
            String y = "";
            compareHeight(x, y);
            compareLowness(x, y);
            compareRoundness(x, y);
            compareBackness(x, y);
            compareATR(x, y);
        }

        private void compareConsonantFeatures() {
            String x = "";
            String y = "";
            compareConsonant(x, y);
            compareSonorant(x, y);
            compareVoice(x, y);
            compareContinuant(x, y);
            compareNasal(x, y);
            compareStrident(x, y);
            compareLateral(x, y);
            compareLabial(x, y);
            compareCoronal(x, y);
            compareAnterior(x, y);
            compareDorsal(x, y);
            compareGlottal(x, y);
        }

        // compare vowel features
        private boolean compareHeight(String x, String y) {
            x = inputList.get(0).high; y = x;
            for (int i = 0; i < inputList.size()-1; i++ ){
                x = inputList.get(i).high;
                y = inputList.get(i+1).high;
                if (!x.equals(y)){
                    return false;
                }
            }
            Log.i(TAG,"走到Height了 high = " + y);
            if (x != "" && y != "") {
                text_vowel_hi.setVisibility(View.VISIBLE);
                text_vowel_hi.setText(x);
            }
            return true;
        }

        private boolean compareLowness(String x, String y) {
            x = inputList.get(0).low; y = x;
            for (int i = 0; i < inputList.size()-1; i++ ){
                x = inputList.get(i).low;
                y = inputList.get(i+1).low;
                if (!x.equals(y)){
                    return false;
                }
            }
            Log.i(TAG,"走到Lowness了 low = " + x);
            if (x != "" && y != "") {
                text_vowel_lo.setVisibility(View.VISIBLE);
                text_vowel_lo.setText(x);
            }
            return true;
        }

        private boolean compareBackness(String x, String y) {
            x = inputList.get(0).back; y = x;
            for (int i = 0; i < inputList.size()-1; i++ ){
                x = inputList.get(i).back;
                y = inputList.get(i+1).back;
                if (!x.equals(y)){
                    return false;
                }
            }
            Log.i(TAG,"走到Backness了 back = " + x);
            if (x != "" && y != "") {
                text_vowel_bk.setVisibility(View.VISIBLE);
                text_vowel_bk.setText(x);
            }
            return true;
        }

        private boolean compareRoundness(String x, String y) {
            x = inputList.get(0).round; y = x;
            for (int i = 0; i < inputList.size()-1; i++ ){
                x = inputList.get(i).round;
                y = inputList.get(i+1).round;
                if (!x.equals(y)){
                    return false;
                }
            }
            Log.i(TAG,"走到Round了 round = " + x);
            if (x != "" && y != "") {
                text_vowel_rd.setVisibility(View.VISIBLE);
                text_vowel_rd.setText(x);
            }
            return true;
        }

        private boolean compareATR(String x, String y) {
            x = inputList.get(0).atr; y = x;
            for (int i = 0; i < inputList.size()-1; i++ ){
                x = inputList.get(i).atr;
                y = inputList.get(i+1).atr;
                if (!x.equals(y)){
                    return false;
                }
            }
            Log.i(TAG,"走到ATR了 atr = " + x);
            if (x != "" && y != ""){
                text_vowel_atr.setVisibility(View.VISIBLE);
                text_vowel_atr.setText(x);
            }
            return true;
        }

        // compare consonant features
        private boolean compareConsonant(String x, String y) {
            x = inputList.get(0).cons;
            for (int i = 0; i < inputList.size()-1; i++ ){
                x = inputList.get(i).cons;
                y = inputList.get(i+1).cons;
                if (!x.equals(y)){
                    return false;
                }
            }
            Log.i(TAG,"inputList里面有： " + inputList.get(0).sound);
            Log.i(TAG,"走到Consonant了 cons = " + x);
            text_consonant_cons.setVisibility(View.VISIBLE);
            text_consonant_cons.setText(x);
            return true;
        }

        private boolean compareSonorant(String x, String y) {
            x = inputList.get(0).son;
            for (int i = 0; i < inputList.size()-1; i++ ){
                x = inputList.get(i).son;
                y = inputList.get(i+1).son;
                if (!x.equals(y)){
                    return false;
                }
            }
            Log.i(TAG,"走到Sonorant了 son = " + x);
            text_consonant_son.setVisibility(View.VISIBLE);
            text_consonant_son.setText(x);
            return true;
        }

        private boolean compareVoice(String x, String y) {
            x = inputList.get(0).voi;
            for (int i = 0; i < inputList.size()-1; i++ ){
                x = inputList.get(i).voi;
                y = inputList.get(i+1).voi;
                if (!x.equals(y)){
                    return false;
                }
            }
            Log.i(TAG,"走到Voice了 voi = " + x);
            text_consonant_voi.setVisibility(View.VISIBLE);
            text_consonant_voi.setText(x);
            return true;
        }

        private boolean compareNasal(String x, String y) {
            x = inputList.get(0).nas;
            for (int i = 0; i < inputList.size()-1; i++ ){
                x = inputList.get(i).nas;
                y = inputList.get(i+1).nas;
                if (!x.equals(y)){
                    return false;
                }
            }
            Log.i(TAG,"走到Nasal了 nas = " + x);
            text_consonant_nas.setVisibility(View.VISIBLE);
            text_consonant_nas.setText(x);
            return true;
        }

        private boolean compareContinuant(String x, String y) {
            x = inputList.get(0).cont;
            for (int i = 0; i < inputList.size()-1; i++ ){
                x = inputList.get(i).cont;
                y = inputList.get(i+1).cont;
                if (!x.equals(y)){
                    return false;
                }
            }
            Log.i(TAG,"走到Continuant了 cont = " + x);
            text_consonant_cont.setVisibility(View.VISIBLE);
            text_consonant_cont.setText(x);
            return true;
        }

        private boolean compareStrident(String x, String y) {
            x = inputList.get(0).strid;
            for (int i = 0; i < inputList.size()-1; i++ ){
                x = inputList.get(i).strid;
                y = inputList.get(i+1).strid;
                if (!x.equals(y)){
                    return false;
                }
            }
            Log.i(TAG,"走到Strident了 strid = " + x);
            text_consonant_strid.setVisibility(View.VISIBLE);
            text_consonant_strid.setText(x);
            return true;
        }

        private boolean compareLateral(String x, String y) {
            x = inputList.get(0).lat;
            for (int i = 0; i < inputList.size()-1; i++ ){
                x = inputList.get(i).lat;
                y = inputList.get(i+1).lat;
                if (!x.equals(y)){
                    return false;
                }
            }
            Log.i(TAG,"走到Lateral了 lat = " + x);
            text_consonant_lat.setVisibility(View.VISIBLE);
            text_consonant_lat.setText(x);
            return true;
        }

        private boolean compareLabial(String x, String y) {
            x = inputList.get(0).lab; y = x;
            for (int i = 0; i < inputList.size()-1; i++ ){
                x = inputList.get(i).lab;
                y = inputList.get(i+1).lab;
                if (!x.equals(y)){
                    return false;
                }
            }
            Log.i(TAG,"走到LABIAL了 LAB = " + x);
            if (x != "" && y != "") {
                text_consonant_lab.setVisibility(View.VISIBLE);
                text_consonant_lab.setText(x);
            }
            return true;
        }

        private boolean compareCoronal(String x, String y) {
            x = inputList.get(0).cor; y = x;
            for (int i = 0; i < inputList.size()-1; i++ ){
                x = inputList.get(i).cor;
                y = inputList.get(i+1).cor;
                if (!x.equals(y)){
                    return false;
                }
            }
            Log.i(TAG, "走到CORONAL了 COR = " + x);
            if (x != "" && y != "") {
                text_consonant_cor.setVisibility(View.VISIBLE);
                text_consonant_cor.setText(x);
            }
            return true;
        }

        private boolean compareAnterior(String x, String y) {
            x = inputList.get(0).ant; y = x;
            for (int i = 0; i < inputList.size()-1; i++ ){
                x = inputList.get(i).ant;
                y = inputList.get(i+1).ant;
                if (!x.equals(y)){
                    return false;
                }
            }
            Log.i(TAG,"走到anterior了 ant = " + x);
            if (x != "" && y != ""){
                text_consonant_ant.setVisibility(View.VISIBLE);
                text_consonant_ant.setText(x);
            }
            return true;
        }

        private boolean compareDorsal(String x, String y) {
            x = inputList.get(0).dors; y = x;
            for (int i = 0; i < inputList.size()-1; i++ ){
                x = inputList.get(i).dors;
                y = inputList.get(i+1).dors;
                if (!x.equals(y)){
                    return false;
                }
            }
            Log.i(TAG,"走到DORSAL了 DORS = " + x);
            if (x != "" && y != "") {
                text_consonant_dors.setVisibility(View.VISIBLE);
                text_consonant_dors.setText(x);
            }
            return true;
        }

        private boolean compareGlottal(String x, String y) {
            x = inputList.get(0).glot; y = x;
            for (int i = 0; i < inputList.size()-1; i++ ){
                x = inputList.get(i).glot;
                y = inputList.get(i+1).glot;
                if (!x.equals(y)){
                    return false;
                }
            }
            Log.i(TAG, "走到GLOTTAL了 GLOT = " + x);
            if (x != "" && y != "") {
                text_consonant_glot.setVisibility(View.VISIBLE);
                text_consonant_glot.setText(x);
            }
            return true;
        }

        private List<String> mergeLists(ArrayList<String>... list){
            ArrayList<String> list_combined = new ArrayList<String>();
            for (ArrayList<String> inputList: list){
                list_combined.addAll(inputList);
            }
            return list_combined;
        }

        private void reset(){
            text_consonant_cons.setVisibility(View.GONE);
            text_consonant_son.setVisibility(View.GONE);
            text_consonant_voi.setVisibility(View.GONE);
            text_consonant_cont.setVisibility(View.GONE);
            text_consonant_nas.setVisibility(View.GONE);
            text_consonant_strid.setVisibility(View.GONE);
            text_consonant_lat.setVisibility(View.GONE);
            text_consonant_lab.setVisibility(View.GONE);
            text_consonant_cor.setVisibility(View.GONE);
            text_consonant_ant.setVisibility(View.GONE);
            text_consonant_dors.setVisibility(View.GONE);
            text_consonant_glot.setVisibility(View.GONE);

            text_vowel_hi.setVisibility(View.GONE);
            text_vowel_lo.setVisibility(View.GONE);
            text_vowel_bk.setVisibility(View.GONE);
            text_vowel_rd.setVisibility(View.GONE);
            text_vowel_atr.setVisibility(View.GONE);

            input = "";
            inputList = new ArrayList<>();
        }
    }

