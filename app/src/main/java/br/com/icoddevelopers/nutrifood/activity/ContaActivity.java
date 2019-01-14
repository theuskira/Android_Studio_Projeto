package br.com.icoddevelopers.nutrifood.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.com.icoddevelopers.nutrifood.R;
import br.com.icoddevelopers.nutrifood.config.ConfiguracaoFirebase;

public class ContaActivity extends AppCompatActivity {

    private TextView email;

    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conta);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Conta");     //Titulo para ser exibido na sua Action Bar em frente à seta

        this.email = findViewById(R.id.txtEmailPerfil);

        verificarDados();
    }

    private void verificarDados(){
        try{
            autenticacao = ConfiguracaoFirebase.getFirebaseAuth();
            //Toast.makeText(MainActivity.this, "" + autenticacao.getCurrentUser().getEmail(), Toast.LENGTH_LONG).show();

            FirebaseUser usuarioAtual = null;
            if(autenticacao.getCurrentUser() != null){
                usuarioAtual = autenticacao.getCurrentUser();
            }

            if(usuarioAtual != null){
                email.setText(usuarioAtual.getEmail());
            }else{
                startActivity(new Intent(this, LoginActivity.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finish();
            }
        }catch (Exception e){
            Toast.makeText(ContaActivity.this, "Erro: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_conta, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar

        int id = item.getItemId();

        if(id == android.R.id.home){
            startActivity(new Intent(this, MainActivity.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
            finish();  //Método para matar a activity e não deixa-lá indexada na pilhagem
            return true;
        }else if(id == R.id.btnMenuConta_Editar){
            Toast.makeText(this,"Editar!", Toast.LENGTH_LONG).show();
        }
        else if(id == R.id.btnMenuConta_Deslogar){
            Toast.makeText(this,"Deslogar!", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }
}
