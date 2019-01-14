package br.com.icoddevelopers.nutrifood.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import br.com.icoddevelopers.nutrifood.R;
import br.com.icoddevelopers.nutrifood.config.ConfiguracaoFirebase;
import br.com.icoddevelopers.nutrifood.model.Usuario;

public class LoginActivity extends AppCompatActivity {

    private EditText campoEmail, campoSenha;
    private FirebaseAuth autenticacao;
    private ProgressBar progressBar;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Entrar");     //Titulo para ser exibido na sua Action Bar em frente à seta

        this.campoEmail = findViewById(R.id.txtCadLogin_Email);
        this.campoSenha = findViewById(R.id.txtCadLogin_Senha);
        this.progressBar = findViewById(R.id.login_progress);
        this.scrollView = findViewById(R.id.login_form);

        autenticacao = ConfiguracaoFirebase.getFirebaseAuth();


    }

    public void logarUsuario(Usuario usuario){

        scrollView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        autenticacao.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            abrirTelaPrincipal();
                            finish();
                        }else {
                            String excessao = "";
                            try{
                                throw task.getException();
                            }catch (FirebaseAuthInvalidUserException e){
                                excessao = "Usuário não está cadastrado!";
                            }catch (FirebaseAuthInvalidCredentialsException e){
                                excessao = "E-mail e senha não correspondem a um usuário cadastrado!";
                            }catch (FirebaseNetworkException e){
                                excessao = "Verifique sua conexão!";
                            }catch (Exception e){
                                excessao = "Erro ao cadastrar usário: " + e.getMessage();
                                e.printStackTrace();
                            }
                            Toast.makeText(LoginActivity.this, excessao, Toast.LENGTH_LONG).show();
                            scrollView.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });
    }

    public void validarAutenticacaoUsuario(View view){

        if(!validarCampos()){
            Usuario usuario = new Usuario();
            usuario.setEmail(campoEmail.getText().toString());
            usuario.setSenha(campoSenha.getText().toString());

            logarUsuario(usuario);
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

    public void abrirTelaPrincipal(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
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
