package com.example.plwang13.androidpicturematch;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by plwang13 on 10/2/2016.
 * Cite: UltimateTicTacToe, ImageLoader, StopWatch, www.bigsoundbank.com
 */

public class MainFragment extends Fragment {


    static private int mLargeIds[] = {R.id.large1, R.id.large2, R.id.large3,
            R.id.large4, R.id.large5, R.id.large6, R.id.large7, R.id.large8,
            R.id.large9, R.id.large10, R.id.large11, R.id.large12, R.id.large13,
            R.id.large14, R.id.large15, R.id.large16, R.id.large17, R.id.large18, R.id.large19, R.id.large20};

    List<Drawable> poke = new ArrayList<Drawable>();
    List<Drawable> pokePics = new ArrayList<Drawable>();
    List<Drawable> pokeball = new ArrayList<Drawable>();
    ArrayList<Drawable> comp = new ArrayList<Drawable>();
    ArrayList<ImageButton> img = new ArrayList<ImageButton>();
    Handler hand = new Handler();
    int counter = 1;
    int click = 0;
    SoundPool sp = null;
    int hedgehogSound = 0;
    int crowSound = 0;
    int winSound = 0;
    Handler h = new Handler();
    TextView count = null;
    int endCount = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        this.sp = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
        this.hedgehogSound = this.sp.load(getContext(), R.raw.hedgehog, 1);
        this.crowSound = this.sp.load(getContext(), R.raw.crow, 1);
        this.winSound = this.sp.load(getContext(), R.raw.win, 1);
        this.h = new Handler();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container);
        this.count = (TextView) rootView.findViewById(R.id.score);
        getPic();

        for (int i = 0; i < 20; i++) {
            final ImageButton ib = (ImageButton) rootView.findViewById(mLargeIds[i]);
            final int temp = i;
            ib.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    ib.setImageDrawable(pokePics.get(temp));
                    comp.add(pokePics.get(temp));
                    img.add(ib);
                    count.setText(Integer.toString(click));


                    if (counter % 2 == 0) {
                        hand.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // match
                                if (compare(comp) == true) {
                                    ImageButton ib1 = img.get(1);
                                    ib1.setImageDrawable(comp.get(1));
                                    ib1.setEnabled(false);
                                    ImageButton ib2 = img.get(0);
                                    ib1.setEnabled(false);
                                    ib2.setImageDrawable(comp.get(0));
                                    comp.clear();
                                    img.clear();
                                    sp.play(hedgehogSound, 1f, 1f, 1, 0, 1);
                                    click++;
                                    endCount++;
                                } else {
                                    // not a match
                                    ImageButton ib1 = img.get(1);
                                    ib1.setImageDrawable(pokeball.get(0));
                                    ImageButton ib2 = img.get(0);
                                    ib2.setImageDrawable(pokeball.get(0));
                                    comp.clear();
                                    img.clear();
                                    sp.play(crowSound, 1f, 1f, 1, 0, 1);
                                    click++;
                                }

                                if (endCount == 10) {
                                    sp.play(winSound, 1f, 1f, 1, 0, 1);
                                }
                            }
                        }, 400);
                    }
                    counter++;
                }
            });
        }
        return rootView;
    }

    private Boolean compare(ArrayList<Drawable> draw) {
        Drawable.ConstantState first = draw.get(0).getConstantState();
        Drawable.ConstantState second = draw.get(1).getConstantState();
        if (first.equals(second)) {
            return true;
        }
        else{
            return false;
        }
    }

    private void getPic() {
        int id = 0;
        int imNumb = 0;
        int bll = getResources().getIdentifier("pokeball", "drawable", getActivity().getPackageName());

        while (imNumb < 11) {
            id = getResources().getIdentifier("image" + imNumb, "drawable", getActivity().getPackageName());
            if (id != 0) {
                Drawable dr = getResources().getDrawable(id);
                poke.add(dr);
            }
            imNumb++;
        }
        Collections.shuffle(poke);
        for (int i = 0; i < 10; i++) {
            pokePics.add(poke.get(i));
            pokePics.add(poke.get(i));
        }
        Collections.shuffle(pokePics);
        Drawable ball = getResources().getDrawable(bll);
        pokeball.add(ball);
    }

}
