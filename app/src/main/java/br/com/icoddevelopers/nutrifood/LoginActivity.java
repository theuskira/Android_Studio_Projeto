package br.com.icoddevelopers.nutrifood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private Button btnCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Entrar");     //Titulo para ser exibido na sua Action Bar em frente à seta

        this.btnCadastro = findViewById(R.id.btnCadastrar);

        this.btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, CadastroPessoaActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar

        int id = item.getItemId();

        if(id == android.R.id.home){
            startActivity(new Intent(this, MainActivity.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
            finish();  //Método para matar a activity e não deixa-lá indexada na pilhagem
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
