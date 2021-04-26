package br.edu.ifsp.scl.ads.pdm.intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import br.edu.ifsp.scl.ads.pdm.intent.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    //instancia da classe de View Binding
    private ActivityMainBinding activityMainBinding;

    // Constante para passagem de parametro e retorno
    public static final String PARAMETRO = "PARAMETRO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());

        getSupportActionBar().setTitle("Tratando Intents");
        getSupportActionBar().setSubtitle("Tem subtitulo tambem");

        setContentView(activityMainBinding.getRoot());
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.outraActivityMi:
                // Abrir a OutraActivity
                Intent outraActivityIntent = new Intent(this, OutraActivity.class);

                // Forma #1 de passagem de parametros
                Bundle parametrosBundle = new Bundle();
                parametrosBundle.putString(PARAMETRO, activityMainBinding.parametroEt.getText().toString());
                outraActivityIntent.putExtras(parametrosBundle);
                startActivity(outraActivityIntent);

                return true;
            case R.id.viewMi:
                return true;
        }
        return false;
    }
}