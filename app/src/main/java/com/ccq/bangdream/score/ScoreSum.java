package com.ccq.bangdream.score;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;
import com.ccq.bangdream.R;

public class ScoreSum extends AppCompatActivity {
    private ArrayAdapter<String> memberAdapter = null;
    private Integer[][] intMember = new Integer[][]{
            {R.string.kasumi, R.string.arisa, R.string.saaya, R.string.rimi, R.string.tae},
            {R.string.ran, R.string.himari, R.string.moka, R.string.tsugumi, R.string.tomoe},
            {R.string.yukina, R.string.rinko, R.string.ako, R.string.risa, R.string.sayo},
            {R.string.aya, R.string.chisato, R.string.hina, R.string.ibu, R.string.maya},
            {R.string.kokoro, R.string.kanon, R.string.kaoru, R.string.hagumi, R.string.misaki}
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.score);
        setContentView(R.layout.activity_score);
        Spinner band = findViewById(R.id.band);

        band.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String[][] strMember = new String[5][5];
                for (int j = 0; j < 5; j++) {
                    for (int k = 0; k < 5; k++) {
                        strMember[j][k] = getResources().getString(intMember[j][k]);
                    }
                }
                memberAdapter = new ArrayAdapter<>(ScoreSum.this, android.R.layout.simple_spinner_item, strMember[i]);
                Spinner member = findViewById(R.id.member);
                member.setAdapter(memberAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
}
