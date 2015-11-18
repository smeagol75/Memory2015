package com.elcacharreo.android.memory2015;

import android.media.AudioManager;
import android.media.Image;
import android.media.SoundPool;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SoundPool sp;
    int iAplauso;
    int iEvil;
    int iAcierto;
    TableLayout tl;
    Chronometer crono;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tl=(TableLayout)findViewById(R.id.tableLayout);

        tl.setVisibility(View.INVISIBLE);

        crono=(Chronometer)findViewById(R.id.chronometer);

        // TODO: cargar sonidos
   /*     if((android.os.Build.VERSION.SDK_INT) >= 21){
            SoundPool.Builder sp21 = new SoundPool.Builder();
            sp21.setMaxStreams(5);
            sp = sp21.build();
        }
        else */
        {
            sp = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);

        }
         iAplauso=sp.load(this,R.raw.applause,1);
         iEvil=sp.load(this,R.raw.evil,0);
         iAcierto=sp.load(this,R.raw.sonido_acierto,0);

        reparteCartas(); //CAMBIOS PARA CARTAS ALEATORIAS


    }

    // CAMBIOS PARA CARTAS ALEATORIAS: Antes iCartasDibujos
    int []iDibujos={R.drawable.conejo,R.drawable.oveja,R.drawable.pollo,
            R.drawable.conejo,R.drawable.oveja,R.drawable.pollo,
            R.drawable.rinoceronte,R.drawable.serpiente,R.drawable.tiburon,
            R.drawable.rinoceronte,R.drawable.serpiente,R.drawable.tiburon
    };  // Guarda la imagen de cada boton
    int []idBoton={R.id.iv11,R.id.iv12,R.id.iv13,
                  R.id.iv21,R.id.iv22,R.id.iv23,
                  R.id.iv31,R.id.iv32,R.id.iv33,
                  R.id.iv41,R.id.iv42,R.id.iv43}; // Guarda el id de los botones

    int []iCartas=new int[12]; // CAMBIOS PARA CARTAS ALEATORIAS

    int iPuntuacion=0;
    int iContadorCartasAcertadas=0;
    int iTiempo=0;

    // CAMBIOS PARA CARTAS ALEATORIAS

    int nCartasDisponibles()
    {
        int nCartasDisponibles=0;
        for(int i=0;i<12;i++)
        {
            if(iCartas[i]==0)
            {   nCartasDisponibles++;       }
        }
        return nCartasDisponibles;
    }

    void reparteCartas()
    {
        for(int i=0;i<12;i++)
        { iCartas[i]=0; }
        int iDibujoToca=0;

        while(nCartasDisponibles()>0)
        {
            int iCartaAleatoria=(int)(Math.random()*12);
            if(iCartas[iCartaAleatoria]==0)
            {
                iCartas[iCartaAleatoria]=iDibujos[iDibujoToca];
                iDibujoToca++;
            }
        }
    }
    // FIN CAMBIOS PARA CARTAS ALEATORIAS

    void inicializacion()
    {
        // TODO: rellenar aleatoriamente (rellenar imagenBoton )

        reparteCartas();  // CAMBIOS PARA CARTAS ALEATORIAS

        // TODO: puntuacion a 0
        iPuntuacion=0;

        // TODO: tiempo a 0
        iTiempo=0;
    }

    public void botonStop(View v)
    {
        crono.stop();
        tl.setVisibility(View.INVISIBLE);
    }

    public void botonStart(View v)
    {
        inicializacion();

        // TODO: hacer visible Las cartas (tablelayout.setVisibility(Visible) );
        crono.start();

        tl.setVisibility(View.VISIBLE);

    }
    boolean bEsLaPrimera=true; // Para saber si es la primera o 2ª carta que volteamos
    int idBotonPrimeroPulsado=0;
    int idImagenPrimerBotonPulsado=0;
    int idBotonSegundoPulsado=0;
    public void clickImagen(View v)
    {
        // TODO: ¿Y si el boton ya esta pulsado?
        // TODO: Buscar boton para ver su imagen

        int iNumeroBotonPulsado=0;
        for(int i=0;i<12;i=i+1)
        {
            if(v.getId()==idBoton[i])
            {
                iNumeroBotonPulsado=i;
                break;
            }
        }

        ImageView ivPulsado=(ImageView)v;

        // TODO: Mostrar imagen (iv.setImageResource(R.drawable.IMAGEN); )
        ivPulsado.setImageResource(iCartas[iNumeroBotonPulsado]);

        if(bEsLaPrimera==true)
        {
            idBotonPrimeroPulsado=v.getId();
            idImagenPrimerBotonPulsado=iCartas[iNumeroBotonPulsado];
            // TODO: No se puede clickear
            bEsLaPrimera=false;

            sp.play(iAcierto,1,1,1,0,1);
        }
        else
        {
            if(idImagenPrimerBotonPulsado==iCartas[iNumeroBotonPulsado])
            {
                iPuntuacion=iPuntuacion+10; // TODO: puntuacion dependiendo del tiempo
                iContadorCartasAcertadas=iContadorCartasAcertadas+1;
                // TODO: actualizar visor puntuacion
                TextView tvPunto=(TextView)findViewById(R.id.tvAnuncio);
                tvPunto.setText(Integer.toString(iPuntuacion));
                // TODO: sonido victoria
                sp.play(iAplauso,1,1,1,0,1);
                // TODO: No clicable la 2ª

                if(iContadorCartasAcertadas==6)
                {
                    crono.stop();
                    sp.play(iAplauso,1,1,1,0,2);
                    tl.setVisibility(View.INVISIBLE);
                }

            }
            else
            {
                // TODO: sonido fracaso total
                sp.play(iEvil,1,1,1,0,1);
                idBotonSegundoPulsado=v.getId();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        ImageView ivSegunda= (ImageView) findViewById(idBotonSegundoPulsado);
                        ivSegunda.setImageResource(R.drawable.interrogacion);
                        ImageView ivPrimera = (ImageView) findViewById(idBotonPrimeroPulsado);
                        ivPrimera.setImageResource(R.drawable.interrogacion);
                    }
                }, 500);
            }

            bEsLaPrimera=true;

        }








    }






}







