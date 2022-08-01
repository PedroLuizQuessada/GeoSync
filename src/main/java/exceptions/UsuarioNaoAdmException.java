package exceptions;

public class UsuarioNaoAdmException extends Exception {
    public UsuarioNaoAdmException() {
        super("Sua conta não tem acesso a esse serviço");
    }
}
