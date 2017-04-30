package com.elcacharreo.android.memory2015;

import android.annotation.TargetApi;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    //Para reproducir sonidos
    SoundPool sp;
    //Diferentes sonidos
    int iAplauso;
    int iEvil;
    int iAcierto;
    //Tablero para cartas
    TableLayout tl;
    //Cronometro para el tiempo
    Chronometer crono;
    //Switch para el sonido
    Switch sw;
    //Para el sonido también
    AudioManager audioManager;


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //vinculamos el tablero
        tl=(TableLayout)findViewById(R.id.tableLayout);
        //Ocultamos el tablero
        tl.setVisibility(View.INVISIBLE);
        //Vinculamos el crono
        crono=(Chronometer)findViewById(R.id.chronometer);

        //controlamos el método soundpool en función del API
       if((android.os.Build.VERSION.SDK_INT) >= 21){
           SoundPool.Builder sp21 = null;
           if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
               sp21 = new SoundPool.Builder();
           }

           if (sp21 != null) {
               sp21.setMaxStreams(5);
               sp = sp21.build();
           }
       }
        else
        {
            sp = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);

        }
        //Cargamos los sonidos
        iAplauso=sp.load(this,R.raw.applause,1);
         iEvil=sp.load(this,R.raw.evil,0);
         iAcierto=sp.load(this,R.raw.sonido_acierto,0);
        //Definimos el Switch para apagar o enceder el sonido
        sw = (Switch) findViewById(R.id.switch1);
        //Lo marcamos encendido
        sw.setChecked(true);
        audioManager = (AudioManager) getSystemService(MainActivity.AUDIO_SERVICE);
        //En la propiedad OnCheckedchange ponemos el sonido en on o en off según nuestro interés
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    audioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
                   //Mensaje de activación
                    Toast.makeText(getApplicationContext(), "Sonido encendido", Toast.LENGTH_SHORT).show();
                } else {
                    audioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
                    //Mensaje de desactivación
                    Toast.makeText(getApplicationContext(), "Sonido apagado", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Defino un spinner para cambiar el color
        Spinner spinner = (Spinner) findViewById(R.id.spColor);
       //Guardo los valores en un arrayde Strings para pasarlo por el adapter
        String[] colores = {"Color Fondo","Rojo","Verde","Azul","Amarillo","Blanco"};
        //Le asigno el adapter con el array
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, colores));
        //y controlo que si cambiamos la selección del spinner cambie el color
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id)
            {
                //Tras asignar una id al relative Layout creo un objeto y lo vinculo
                RelativeLayout milayout= (RelativeLayout) findViewById(R.id.rellay);;
                switch (position) {
                    case 0:
                       //Asigno colores definidos previamente en el recurso color.xml
                        milayout.setBackgroundColor(getResources().getColor(R.color.amarillo));
                        break;
                    case 1:
                        milayout.setBackgroundColor(getResources().getColor(R.color.rojo));
                        break;
                    case 2:
                        milayout.setBackgroundColor(getResources().getColor(R.color.verde));
                        break;
                    case 3:
                        milayout.setBackgroundColor(getResources().getColor(R.color.azul));
                        break;
                    case 4:
                        milayout.setBackgroundColor(getResources().getColor(R.color.amarillo));
                        break;
                    default:
                        milayout.setBackgroundColor(getResources().getColor(R.color.blanco));
                        break;
                }
            }

            @Override//obligatorio ponerla por la herencia
            public void onNothingSelected(AdapterView<?> parent)
            {
                          }
        });
        //Hacemos lo mismo con el spinner para elegir las imágenes que vamos a usar
        Spinner sptipoimagenes = (Spinner) findViewById(R.id.spImagenes);
        String[] tipos = {"Animales","Iria","Patrulla Canina","Peppa Pig", "Soy Luna"};
        sptipoimagenes.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipos));

        sptipoimagenes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id)
            {
                //En función de la elección rellenamos el array iDibujos con un for
                switch (position) {
                    case 0:
                        for(int i=0;i<12;i++){
                            iDibujos[i]=ianimales[i];
                        }
                        break;
                    case 1:
                        for(int i=0;i<12;i++){
                            iDibujos[i]=iamigas[i];
                        }
                        break;

                    case 2:
                        for(int i=0;i<12;i++){
                            iDibujos[i]=ipcanina[i];
                        }
                        break;
                    case 3:
                        for(int i=0;i<12;i++){
                            iDibujos[i]=ipeppapig[i];
                        }
                        break;

                    case 4:
                        for(int i=0;i<12;i++){
                            iDibujos[i]= iluna[i];
                        }
                        break;

                   default:
                       for(int i=0;i<12;i++){
                           iDibujos[i]=ianimales[i];
                       }
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                // vacio

            }
        });


        //Función que reparte cartas aleatoriamente
        reparteCartas();

    }

    //Array inicial de dibujos
    int []iDibujos={R.drawable.conejo,R.drawable.oveja,R.drawable.pollo,
            R.drawable.conejo,R.drawable.oveja,R.drawable.pollo,
            R.drawable.rinoceronte,R.drawable.serpiente,R.drawable.tiburon,
            R.drawable.rinoceronte,R.drawable.serpiente,R.drawable.tiburon
    };
    //Array de animales para poder volver al inicial
    int []ianimales={R.drawable.conejo,R.drawable.oveja,R.drawable.pollo,
            R.drawable.conejo,R.drawable.oveja,R.drawable.pollo,
            R.drawable.rinoceronte,R.drawable.serpiente,R.drawable.tiburon,
            R.drawable.rinoceronte,R.drawable.serpiente,R.drawable.tiburon
    };
    //Array de fotos de mis hijos y amigas para que me hagan de beta tester
    int []iamigas={R.drawable.alba,R.drawable.asier,R.drawable.esther,
            R.drawable.iria,R.drawable.laia,R.drawable.paula,
            R.drawable.alba,R.drawable.asier,R.drawable.esther,
            R.drawable.iria,R.drawable.laia,R.drawable.paula
    };

    int []ipcanina={R.drawable.chase,R.drawable.marshall,R.drawable.rocky,
            R.drawable.rubble,R.drawable.skye,R.drawable.zuma,
            R.drawable.chase,R.drawable.marshall,R.drawable.rocky,
            R.drawable.rubble,R.drawable.skye,R.drawable.zuma
    };
    int []ipeppapig={R.drawable.peppa,R.drawable.george,R.drawable.dinosaurio,
            R.drawable.pappapig,R.drawable.mammapig,R.drawable.rebecca,
            R.drawable.peppa,R.drawable.george,R.drawable.dinosaurio,
            R.drawable.pappapig,R.drawable.mammapig,R.drawable.rebecca
    };

    int []iluna={R.drawable.luna,R.drawable.ambar,R.drawable.gaston,
            R.drawable.nina,R.drawable.mateo,R.drawable.simon,
            R.drawable.luna,R.drawable.ambar,R.drawable.gaston,
            R.drawable.nina,R.drawable.mateo,R.drawable.simon
    };



    // Guarda la imagen de cada boton
    int []idBoton={R.id.iv11,R.id.iv12,R.id.iv13,
                  R.id.iv21,R.id.iv22,R.id.iv23,
                  R.id.iv31,R.id.iv32,R.id.iv33,
                  R.id.iv41,R.id.iv42,R.id.iv43}; // Guarda el id de los botones
    //Iniciamos las variables
    int []iCartas=new int[12]; // CAMBIOS PARA CARTAS ALEATORIAS
    int iPuntuacion=0;
    int iContadorCartasAcertadas=0;
    int iTiempo=0;

//Controla las cartas sin asignar
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
//Asigna las cartas
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

    //Inicialización, al pulsar start lo llamamos para inicializar el juego
    void inicializacion()
    {
        //Ponemos el contador de errores y de aciertos a cero
        icontadorErrores=0;
        iContadorCartasAcertadas=0;
        //Recorremos toda la tabla y le ponemos interrogación cada vez que se inicie en cada view
        for(int i=0;i<12;i++){
           int boton =idBoton[i];
            ImageView  ivtemp= (ImageView) findViewById(boton);
            ivtemp.setImageResource(R.drawable.interrogacion);
            //Le volvemos a dejar accesible ya que al haber acertado todas al principio quedan inaccesibles
            ivtemp.setClickable(true);

        }

        reparteCartas();  // CAMBIOS PARA CARTAS ALEATORIAS
        //Reseteo de la puntuación
        iPuntuacion=0;
        // Reseteo del tiempo
        iTiempo=0;
    }
    //Pulsar el boton Para
    public void botonStop(View v)
    {
        crono.stop();
        tl.setVisibility(View.INVISIBLE);
    }

    public void botonStart(View v)
    {
        //Ponemos la puntuacion a cero
        iPuntuacion=0;
        //Le ponemos la puntuación
        TextView tvPunto=(TextView)findViewById(R.id.tvAnuncio);
        tvPunto.setText(Integer.toString(iPuntuacion));
        //Inicializa la partida
        inicializacion();
        //Arranca el crono
        crono.setBase(SystemClock.elapsedRealtime());
        crono.start();
        //ponemos el tablero visible
        tl.setVisibility(View.VISIBLE);

    }
    boolean bEsLaPrimera=true; // Para saber si es la primera o 2ª carta que volteamos
    int idBotonPrimeroPulsado=0;
    int idImagenPrimerBotonPulsado=0;
    int idBotonSegundoPulsado=0;
    //Contador errores para restar a la puntuación
    int icontadorErrores=0;
    //creo dos imageview para controlar los que han sido pulsados y ponerles o
    // quitarles la propiedad de clickable
    ImageView ivPrimeroPulsado=null;
    ImageView ivSegundoPulsado=null;

    public void clickImagen(View v)
    {
    //Buscamos el botón pulsado
        int iNumeroBotonPulsado=0;
        for(int i=0;i<12;i=i+1)
        {
            if(v.getId()==idBoton[i])
            {
                iNumeroBotonPulsado=i;
                break;
            }
        }
        //Buscamos la imagen del boton
        ImageView ivPulsado=(ImageView)v;
        //Y la ponemos en su imageview
        ivPulsado.setImageResource(iCartas[iNumeroBotonPulsado]);
        //Si es la primera pulsada ...
        if(bEsLaPrimera)
        {
            //Buscamos su identidad
            idBotonPrimeroPulsado=v.getId();
            idImagenPrimerBotonPulsado=iCartas[iNumeroBotonPulsado];
           //Evitamos que el botón esté accesible para darle más
            ivPrimeroPulsado=ivPulsado;
            ivPrimeroPulsado.setClickable(false);
            bEsLaPrimera=false;
            //Sonido
            sp.play(iAcierto,1,1,1,0,1);
        }
        else
        {
            //Si no es la primera
            if(idImagenPrimerBotonPulsado==iCartas[iNumeroBotonPulsado])
            {
               //Evitamos que se pulse de nuevo el segundo
                ivSegundoPulsado=ivPulsado;
                ivSegundoPulsado.setClickable(false);
                iPuntuacion=iPuntuacion+10;
                iContadorCartasAcertadas=iContadorCartasAcertadas+1;
                TextView tvPunto=(TextView)findViewById(R.id.tvAnuncio);
                tvPunto.setText(Integer.toString(iPuntuacion));
                sp.play(iAplauso,1,1,1,0,1);


                if(iContadorCartasAcertadas==6)
                {
                   //Una vez acertadas todas las parejas obtenemos el tiempo y lo paso a entero
                    String TextoCrono = crono.getText().toString();
                    String arrayTexto[] = TextoCrono.split(":");
                    String Minutos = arrayTexto[0];
                    String Segundos = arrayTexto[1];
                    TextoCrono = Minutos + Segundos;

                    int iTiempoTotal= Integer.parseInt(TextoCrono);
                    //Creamos un entero que sea puntuacion total que es lo que va a variar
                    int puntuacionFinal=0;
                   //En funcion del tiempo le asignamos un total
                    if(iTiempoTotal<10){
                       puntuacionFinal=110;
                    }
                    else if ((iTiempoTotal<15)){
                        puntuacionFinal=100;
                     }
                    else if ((iTiempoTotal<20)){
                        puntuacionFinal=90;
                    }
                    else if ((iTiempoTotal<25)){
                        puntuacionFinal=80;
                    }
                    else if ((iTiempoTotal<30)){
                        puntuacionFinal=70;
                    }
                    else  puntuacionFinal=60;
               //Le restamos los errores cometidos
                puntuacionFinal = puntuacionFinal-icontadorErrores;
                   if(puntuacionFinal<10)puntuacionFinal=10;

                    tvPunto.setText(Integer.toString(puntuacionFinal));
                    crono.stop();
                    sp.play(iAplauso,1,1,1,0,2);
                    tl.setVisibility(View.INVISIBLE);
                }

            }
            else
            {
                //Si fallamos
                sp.play(iEvil,1,1,1,0,1);
                idBotonSegundoPulsado=v.getId();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        ImageView ivSegunda= (ImageView) findViewById(idBotonSegundoPulsado);
                        ivSegunda.setImageResource(R.drawable.interrogacion);
                        //En caso de fallo les volvemos a dejar clickables, si aciertan se quedan sin poder hacer click
                        ImageView ivPrimera = (ImageView) findViewById(idBotonPrimeroPulsado);
                        //Volvemos a poner las interrogaciones
                        ivPrimera.setImageResource(R.drawable.interrogacion);

                    }

                },300);
               //Volvemos a dejar sea clickable
                ivPrimeroPulsado.setClickable(true);
                ivPrimeroPulsado.setClickable(true);
                icontadorErrores++;
            }
            //Vuelve a ser la primera la próxima que se pulse
            bEsLaPrimera=true;

        }







    }






}







