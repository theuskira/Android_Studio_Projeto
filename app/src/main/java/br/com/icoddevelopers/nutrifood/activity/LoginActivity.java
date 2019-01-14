package br.com.icoddevelopers.nutrifood.activity;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import br.com.icoddevelopers.nutrifood.R;
import br.com.icoddevelopers.nutrifood.model.Usuario;

public class LoginActivity extends AppCompatActivity {

    private EditText campoEmail, campoSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Entrar");     //Titulo para ser exibido na sua Action Bar em frente à seta

        this.campoEmail = findViewById(R.id.txtCadLogin_Email);
        this.campoSenha = findViewById(R.id.txtCadLogin_Senha);


    }

    public void logarUsuario(View view){

        if(!validarCampos()){
            Usuario usuario = new Usuario();

            usuario.setEmail(campoEmail.getText().toString());
            usuario.setSenha(campoSenha.getText().toString());
        }

    }

    public boolean validarCampos(){
        boolean erro = false;

        if(campoEmail.getText().toString().equals("")){
            campoEmail.setError("Digite um e-mail!");
            erro = true;
        }
        if(campoSenha.getText().toString().equals("")){
            campoSenha.setError("Digite uma senha!");
            erro = true;
        }
        return erro;
    }

    public void abrirTelaCadastro(View view){
        Intent intent = new Intent(LoginActivity.this, CadastroPessoaActivity.class);
        startActivity(intent);
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
