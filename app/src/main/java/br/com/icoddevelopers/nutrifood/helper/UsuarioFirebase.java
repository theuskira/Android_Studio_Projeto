package br.com.icoddevelopers.nutrifood.helper;

import com.google.firebase.auth.FirebaseAuth;

import br.com.icoddevelopers.nutrifood.config.ConfiguracaoFirebase;

public class UsuarioFirebase {

    public static String getIndentificadorUsuario(){

        FirebaseAuth usuario = ConfiguracaoFirebase.getFirebaseAuth();
        String email = usuario.getCurrentUser().getEmail();
        String identificadorUsuario = Base64Custom.codificarBase64(email);

        return identificadorUsuario;

    }

}