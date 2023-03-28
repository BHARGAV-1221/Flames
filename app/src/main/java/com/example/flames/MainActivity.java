package com.example.flames;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {
    enum Relation{
        F("Friends"), L("Lovers"), A("Affection"), M("Marriage"), E("Enemies"), S("Sister");
        private String label;
        Relation(String rel){
            this.label = rel;
        }
        public String getLabel() {
            return label;
        }
        public void setLabel(String label) {
            this.label = label;
        }
    }
    EditText t1, t2;
    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t1 = findViewById(R.id.text1);
        t2 = findViewById(R.id.text2);
        b1 = findViewById(R.id.button);
        b1.setText("See Relationship");
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateInputs(t1.getText().toString(), t2.getText().toString());
                String relation = calculateRelationship(t1.getText().toString(), t2.getText().toString());
                Toast.makeText(getApplicationContext(), relation, Toast.LENGTH_LONG).show();
                clearText(t1.getText().toString(),t2.getText().toString());
            }
        });
    }

    private void showAlert(String s) {
        AlertDialog.Builder b1 = new AlertDialog.Builder(this);
        b1.setTitle("Alert");
        b1.setIcon(android.R.drawable.ic_dialog_alert);
        b1.setMessage(s);
        b1.setCancelable(true);

        b1.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog al = b1.create();
        al.getWindow().getAttributes().windowAnimations = com.google.android.material.R.style.Animation_Material3_SideSheetDialog;
        al.show();
    }

    private void validateInputs(String s1, String s2) {
        if(s1 != null && s1.length() <= 0 && s2 != null && s2.length() <= 0){
            showAlert("Names cannot be empty");
        } else if(s1 == null || s1.length() <= 0 ){
            showAlert("Your name cannot be blank");
        } else if(s2 == null || s2.length() <= 0){
            showAlert("Crush name cannot be blank");
        } else if(s1.equals(s2)){
            showAlert("Both names are unique");
        }
    }

    private String calculateRelationship(String s1, String s2){
        StringBuilder sb1,sb2;
        sb1 = new StringBuilder(s1.toLowerCase());
        sb2 = new StringBuilder(s2.toLowerCase());
        int count = getUncommonCharacters(sb1, sb2);
        return getRelationship(count);
    }

    private static int getUncommonCharacters(StringBuilder sb1, StringBuilder sb2) {
        int i, j, count = 0;
        for(i = 0; i < sb1.length(); i++) {
            boolean found = false;
            for(j = 0; j < sb2.length(); j++) {
                if(sb2.charAt(j) != '#' && sb1.charAt(i) == sb2.charAt(j)) {
                    sb1.setCharAt(i, '#');
                    sb2.setCharAt(j, '#');
                    found = true;
                    break;
                }
            }
            if(!found) {
                count++;
            }
        }
        sb2 = new StringBuilder(sb2.toString().replaceAll("#", ""));
        count += sb2.length();
        return count;
    }

    private static String getRelationship(int c) {
        if(c == 0) {
            return "No relationship found";
        } else if (c == 1) {
            return Relation.S.getLabel();
        } else {
            String relation = "";
            StringBuilder sb = new StringBuilder("FLAMES");
            int limit = 0, loop = 0;
            for(int i = 0; ;) {
                if(loop == c - 1) {
                    if(sb.charAt(i) != '#') {
                        sb.setCharAt(i, '#');
                        limit++;
                        loop = 0;
                    } else {
                        i = (i >= sb.length() - 1) ? 0 : i+1;
                    }
                }else {
                    if(sb.charAt(i) != '#')
                        loop++;
                    i = (i >= sb.length() - 1) ? 0 : i+1;
                }
                if(limit == 5)
                    break;
            }
            relation = sb.toString().replaceAll("#", "");
            return Relation.valueOf(relation).getLabel();
        }
    }

    public void  clearText(String s1 ,String s2) {
if(s1.length()>0 && s2.length()>0){
    t1.setText("");
    t2.setText("");
}

    }
}