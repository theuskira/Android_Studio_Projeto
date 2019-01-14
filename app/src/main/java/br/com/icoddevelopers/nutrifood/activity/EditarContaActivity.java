package br.com.icoddevelopers.nutrifood.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.com.icoddevelopers.nutrifood.R;

public class EditarContaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_conta);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Editar conta");     //Titulo para ser exibido na sua Action Bar em frente à seta
    }
}
