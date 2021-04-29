package br.edu.ifsp.scl.ads.pdm.intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import br.edu.ifsp.scl.ads.pdm.intent.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    //instancia da classe de View Binding
    private ActivityMainBinding activityMainBinding;

    // Constante para passagem de parametro e retorno
    public static final String PARAMETRO = "PARAMETRO";
    private final int OUTRA_ACTIVITY_REQUEST_CODE = 0;
    private final int CALL_PHONE_PERMISSION_REQUEST_CODE = 1;
    private final int PICK_IMAGE_FILE_REQUEST_CODE = 2;

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
                //Intent outraActivityIntent = new Intent(this, OutraActivity.class);

                Intent outraActivityIntent = new Intent("RECEBER_E_RETORNAR_ACTION");

                // Forma #1 de passagem de parametros
                Bundle parametrosBundle = new Bundle();
                parametrosBundle.putString(PARAMETRO, activityMainBinding.parametroEt.getText().toString());
                outraActivityIntent.putExtras(parametrosBundle);

                // Forma #2 de passagem de parametros
                //outraActivityIntent.putExtra(PARAMETRO, activityMainBinding.parametroEt.getText().toString());

                startActivityForResult(outraActivityIntent, OUTRA_ACTIVITY_REQUEST_CODE);

                return true;
            case R.id.viewMi:
                // Abrindo navegador
                Intent abrirNavegadorIntent = new Intent(Intent.ACTION_VIEW);
                abrirNavegadorIntent.setData(Uri.parse(activityMainBinding.parametroEt.getText().toString()));
                startActivity(abrirNavegadorIntent);
                return true;
            case R.id.callMi:
                verifyCallPhonePermission();
                return true;
            case R.id.dialMi:
                Intent discarIntent = new Intent(Intent.ACTION_DIAL);
                discarIntent.setData(Uri.parse("tel:" + activityMainBinding.parametroEt.getText().toString()));
                startActivity(discarIntent);
                return true;
            case R.id.pickMi:
                Intent pegarImagemIntent = new Intent(Intent.ACTION_PICK);
                String diretorioImagens = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath();
                pegarImagemIntent.setDataAndType(Uri.parse(diretorioImagens), "image/*");
                startActivityForResult(pegarImagemIntent, PICK_IMAGE_FILE_REQUEST_CODE);
                return true;
        }
        return false;
    }

    private void verifyCallPhonePermission() {
        Intent ligarIntent = new Intent(Intent.ACTION_CALL);
        ligarIntent.setData(Uri.parse("tel:" + activityMainBinding.parametroEt.getText().toString()));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                startActivity(ligarIntent);
            } else {
                // Solicitar permissao para o usuario em tempo de execucao
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, CALL_PHONE_PERMISSION_REQUEST_CODE);
            }
        } else {
            startActivity(ligarIntent);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == CALL_PHONE_PERMISSION_REQUEST_CODE) {
            if(permissions[0].equals(Manifest.permission.CALL_PHONE) && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permissao de ligacao é necessarioa para essa funcionalidade", Toast.LENGTH_SHORT).show();
            }
            verifyCallPhonePermission();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Recebendo retorno da outra activity
        if (requestCode == OUTRA_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            String retorno = data.getStringExtra(OutraActivity.RETORNO);
            if (retorno != null) {
                activityMainBinding.retornoTv.setText(retorno);
            }
        } else {
            if(requestCode == PICK_IMAGE_FILE_REQUEST_CODE && resultCode == RESULT_OK) {
                Uri imageUri = data.getData();

                // Visualizando imagem
                Intent visualizarImagem = new Intent(Intent.ACTION_VIEW, imageUri);
                startActivity(visualizarImagem);
            }
        }
    }
}