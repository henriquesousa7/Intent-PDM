package br.edu.ifsp.scl.ads.pdm.intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import br.edu.ifsp.scl.ads.pdm.intent.databinding.ActivityMainBinding;
import br.edu.ifsp.scl.ads.pdm.intent.databinding.ActivityOutraBinding;

public class OutraActivity extends AppCompatActivity {
    //instancia da classe de View Binding
    private ActivityOutraBinding activityOutraBinding;

    public static final String RETORNO = "RETORNO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityOutraBinding = ActivityOutraBinding.inflate(getLayoutInflater());
        setContentView(activityOutraBinding.getRoot());

        // Recebendo parametros pela Forma #1
        Bundle parametrosBundle = getIntent().getExtras();
        if(parametrosBundle != null) {
            String parametro = parametrosBundle.getString(MainActivity.PARAMETRO, "");
            activityOutraBinding.recebidoTv.setText(parametro);
        }

        // Recebendo parametros pela form #2
        //String parametro = getIntent().getStringExtra(MainActivity.PARAMETRO);
        //if (parametro != null) {
        //activityOutraBinding.recebidoTv.setText(parametro);
        //}

        Log.v(R.string.app_name + "/" + getLocalClassName(), "OnCreate: Iniciando ciclo completo");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v(R.string.app_name + "/" + getLocalClassName(), "OnStart: Iniciando ciclo visivel");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(R.string.app_name + "/" + getLocalClassName(), "OnResume: Iniciando ciclo foreground");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(R.string.app_name + "/" + getLocalClassName(), "OnPause: Finalizando ciclo foreground");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v(R.string.app_name + "/" + getLocalClassName(), "OnStop: Finalizando ciclo visivel");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(R.string.app_name + "/" + getLocalClassName(), "OnDestroy: Finalizando ciclo completo");
    }

    public void onClick(View view) {

        Intent retornoIntent = new Intent();
        retornoIntent.putExtra(RETORNO, activityOutraBinding.retornoEt.getText().toString());
        setResult(RESULT_OK, retornoIntent);

        finish(); // Chama na sequencia onPause, onStop, onDestroy
    }
}