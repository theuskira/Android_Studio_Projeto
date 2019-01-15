package br.com.icoddevelopers.nutrifood.activity;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.com.icoddevelopers.nutrifood.R;
import br.com.icoddevelopers.nutrifood.config.ConfiguracaoFirebase;
import br.com.icoddevelopers.nutrifood.helper.UsuarioFirebase;
import de.hdodenhof.circleimageview.CircleImageView;

public class EditarContaActivity extends AppCompatActivity {

    private CircleImageView circleImageViewPerfil;

    private FirebaseAuth autenticacao;

    private EditText nome;
    private EditText email;
    private EditText telefone;
    private EditText peso;
    private EditText altura;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_conta);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Editar conta");     //Titulo para ser exibido na sua Action Bar em frente à seta

        autenticacao = ConfiguracaoFirebase.getFirebaseAuth();

        FirebaseUser usuario = UsuarioFirebase.getUsuarioAtual();
        Uri url = usuario.getPhotoUrl();

        this.nome = findViewById(R.id.editarNome);
        this.email = findViewById(R.id.editarEmail);
        this.telefone = findViewById(R.id.editarTelefone);
        this.peso = findViewById(R.id.editarPesoAtual);
        this.altura = findViewById(R.id.editarAlturaAtual);

        this.circleImageViewPerfil = findViewById(R.id.circleImageViewEditFotoPerfil);

        if(url != null){
            Glide.with(EditarContaActivity.this)
                    .load(url)
                    .into(circleImageViewPerfil);
        }else {
            circleImageViewPerfil.setImageResource(R.drawable.apple);
        }

        retornarValores();
    }

    private void retornarValores(){

        email.setText(autenticacao.getCurrentUser().getEmail());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.salvar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.btnMenuCadastrar){
            //validarCadastroUsuario();
        }

        return super.onOptionsItemSelected(item);
    }
}
