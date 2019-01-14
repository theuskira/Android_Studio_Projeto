package br.com.icoddevelopers.nutrifood.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.icoddevelopers.nutrifood.config.ConfiguracaoFirebase;
import br.com.icoddevelopers.nutrifood.R;
import br.com.icoddevelopers.nutrifood.model.Usuario;

public class CadastroPessoaActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;

    private EditText cadastroNome;
    private EditText cadastroSobrenome;
    private EditText cadastroEmail;
    private EditText cadastroNumero;
    private EditText cadastroSenha;
    private ProgressBar progressBar;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_pessoa);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Cadastrar");     //Titulo para ser exibido na sua Action Bar em frente à seta

        this.cadastroNome = findViewById(R.id.cadastroNome);
        this.cadastroSobrenome = findViewById(R.id.cadastroSobrenome);
        this.cadastroEmail = findViewById(R.id.cadastroEmail);
        this.cadastroNumero = findViewById(R.id.cadastroTelefone);
        this.cadastroSenha = findViewById(R.id.cadastroSenha);
        this.progressBar = findViewById(R.id.progressBarCad_Usuario);
        this.scrollView = findViewById(R.id.scrollViewCad_Usuario);


    }

    public void cadastrarUsuario(final Usuario usuario){

        scrollView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        autenticacao = ConfiguracaoFirebase.getFirebaseAuth();
        autenticacao.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Sucesso ao cadastrar " + usuario.getNome() + "!", Toast.LENGTH_LONG).show();
                            finish();
                        }else {
                            scrollView.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            String excessao = "";
                            try{
                                throw task.getException();
                            }catch (FirebaseAuthWeakPasswordException e){
                                excessao = "Digite uma senha mais forte!";
                                cadastroSenha.setError(excessao);
                            }catch (FirebaseAuthInvalidCredentialsException e){
                                excessao = "Por favor, digite um e-mail válido!";
                                cadastroEmail.setError(excessao);
                            }catch (FirebaseAuthUserCollisionException e){
                                excessao = "Esta conta já foi cadastrada!";
                                cadastroEmail.setError(excessao);
                            }catch (FirebaseNetworkException e){
                                excessao = "Verifique sua conexão!";
                            }catch (Exception e){
                                excessao = "Erro ao cadastrar usuário: " + e.getMessage();
                                e.printStackTrace();
                            }

                            Toast.makeText(CadastroPessoaActivity.this, excessao, Toast.LENGTH_LONG).show();
                        }

                    }
                });

    }

    public void validarCadastroUsuario(View view){

        boolean erro = false;
        Usuario usuario = new Usuario();

        if(cadastroNome.getText().toString().equals("")){
            this.cadastroNome.setError("Digite o Nome!");
            erro = true;
        }else{
            usuario.setNome(cadastroNome.getText().toString());
        }
        if(cadastroEmail.getText().toString().equals("")){
            this.cadastroEmail.setError("Digite o Email!");
            erro = true;
        }else{
            usuario.setEmail(cadastroEmail.getText().toString());
        }
        if(cadastroSenha.getText().toString().equals("")){
            this.cadastroSenha.setError("Digite a Senha!");
            erro = true;
        }else {
            usuario.setSenha(cadastroSenha.getText().toString());
        }

        if(!erro){
            if(!cadastroSobrenome.getText().toString().equals("")){
                usuario.setSobreNome(cadastroSobrenome.getText().toString());
            }
            if(!cadastroNumero.getText().toString().equals("")){
                usuario.setNumero(Long.parseLong(cadastroNumero.getText().toString()));
            }

            cadastrarUsuario(usuario);

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar

        int id = item.getItemId();

        if(id == android.R.id.home){
            startActivity(new Intent(this, LoginActivity.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
            finish();  //Método para matar a activity e não deixa-lá indexada na pilhagem
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
