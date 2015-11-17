package com.elcacharreo.android.memory2015;

import android.media.Image;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    int []imagenBoton={R.drawable.conejo,R.drawable.oveja,R.drawable.pollo,
            R.drawable.conejo,R.drawable.oveja,R.drawable.pollo,
            R.drawable.rinoceronte,R.drawable.serpiente,R.drawable.tiburon,
            R.drawable.rinoceronte,R.drawable.serpiente,R.drawable.tiburon
    };  // Guarda la imagen de cada boton
    int []idBoton={R.id.iv11,R.id.iv12,R.id.iv13,
                  R.id.iv21,R.id.iv22,R.id.iv23,
                  R.id.iv31,R.id.iv32,R.id.iv33,
                  R.id.iv41,R.id.iv42,R.id.iv43}; // Guarda el id de los botones

    int iPuntuacion=0;
    int iTiempo=0;
    void inicializacion()
    {
        // TODO: rellenar aleatoriamente (rellenar imagenBoton )

        // TODO: cargar sonidos

        // TODO: puntuacion a 0
        iPuntuacion=0;

        // TODO: tiempo a 0
        iTiempo=0;
    }

    public void botonStart(View v)
    {
        inicializacion();

        // TODO: hacer visible Las cartas (tablelayout.setVisibility(Visible) );

    }
    boolean bEsLaPrimera=true;
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
        ivPulsado.setImageResource(imagenBoton[iNumeroBotonPulsado]);


        if(bEsLaPrimera==true)
        {
            idBotonPrimeroPulsado=v.getId();
            idImagenPrimerBotonPulsado=imagenBoton[iNumeroBotonPulsado];
            // TODO: No se puede clickear
            bEsLaPrimera=false;
        }
        else
        {
            if(idImagenPrimerBotonPulsado==imagenBoton[iNumeroBotonPulsado])
            {
                iPuntuacion=iPuntuacion+10;
                // TODO: actualizar visor puntuacion
                // TODO: sonido victoria
                // TODO: No cliceable la 2ª
            }
            else
            {
                // TODO: sonido fracaso total
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







