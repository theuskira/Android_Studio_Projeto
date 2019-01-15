package br.com.icoddevelopers.nutrifood.activity;

import android.Manifest;
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
import android.widget.RadioButton;
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

import br.com.icoddevelopers.nutrifood.activity.helper.Base64Custom;
import br.com.icoddevelopers.nutrifood.activity.helper.Permissao;
import br.com.icoddevelopers.nutrifood.config.ConfiguracaoFirebase;
import br.com.icoddevelopers.nutrifood.R;
import br.com.icoddevelopers.nutrifood.model.Usuario;

public class CadastroPessoaActivity extends AppCompatActivity {

    private String[] permissoesNecessarias = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    private FirebaseAuth autenticacao;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Usuarios");

    private EditText cadastroNome, cadastroEmail, cadastroNumero, cadastroSenha, cadastroPeso, cadastroAltura;
    private RadioButton radioButtonMasculino, radioButtonFeminino;
    private ProgressBar progressBar;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_pessoa);

        //Validar Permissões
        Permissao.validarPermissoes(permissoesNecessarias, this, 1);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Cadastrar");     //Titulo para ser exibido na sua Action Bar em frente à seta

        this.cadastroNome = findViewById(R.id.cadastroNome);
        this.cadastroEmail = findViewById(R.id.cadastroEmail);
        this.cadastroNumero = findViewById(R.id.cadastroTelefone);
        this.cadastroSenha = findViewById(R.id.cadastroSenha);
        this.cadastroPeso = findViewById(R.id.cadastroPesoAtual);
        this.cadastroAltura = findViewById(R.id.cadastroAlturaAtual);
        this.radioButtonMasculino = findViewById(R.id.radioButtonCadMasculino);
        this.radioButtonFeminino = findViewById(R.id.radioButtonCadFeminino);
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
                            chamarMain();

                            try{

                                String indentificadorUsuario = Base64Custom.codificarBase64(usuario.getEmail());
                                usuario.setId(indentificadorUsuario);

                                databaseReference.child(usuario.getId()).child("Email").setValue(usuario.getEmail());
                                databaseReference.child(usuario.getId()).child("Nome").setValue(usuario.getNome());
                                databaseReference.child(usuario.getId()).child("Numero").setValue(usuario.getNumero());
                                if(!usuario.getSexo().equals("")){
                                    databaseReference.child(usuario.getId()).child("Sexo").setValue(usuario.getSexo());
                                }
                                if(!cadastroAltura.getText().toString().equals("")){
                                    databaseReference.child(usuario.getId()).child("Altura").setValue(usuario.getAltura());
                                }
                                if(!cadastroPeso.getText().toString().equals("")){
                                    databaseReference.child(usuario.getId()).child("Peso").setValue(usuario.getPeso());
                                }
                            }catch (Exception e){
                                Toast.makeText(CadastroPessoaActivity.this, "Erro ao salvar informações: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }
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

    public void validarCadastroUsuario(){

        boolean erro = false;
        Usuario usuario = new Usuario();

        usuario.setNome("");
        usuario.setEmail("");
        usuario.setSenha("");
        usuario.setSexo("");

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
        if(cadastroNumero.getText().toString().equals("")){
            this.cadastroNumero.setError("Digite o Numero!");
            erro = true;
        }else{
            usuario.setNumero(Long.parseLong(cadastroNumero.getText().toString()));
        }
        if(cadastroSenha.getText().toString().equals("")){
            this.cadastroSenha.setError("Digite a Senha!");
            erro = true;
        }else {
            usuario.setSenha(cadastroSenha.getText().toString());
        }

        if(!erro){
            if(!cadastroPeso.getText().toString().equals("")){
                usuario.setPeso(Float.parseFloat(cadastroPeso.getText().toString()));
            }
            if(!cadastroAltura.getText().toString().equals("")){
                usuario.setAltura(Float.parseFloat(cadastroAltura.getText().toString()));
            }
            if(radioButtonMasculino.isChecked()){
                usuario.setSexo("Masculino");
            }
            if(radioButtonFeminino.isChecked()){
                usuario.setSexo("Feminino");
            }

            cadastrarUsuario(usuario);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.cadastrar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.btnMenuCadastrar){
            validarCadastroUsuario();
        }

        return super.onOptionsItemSelected(item);
    }

    private void chamarMain(){
        Intent intent = new Intent(CadastroPessoaActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
