package exceptions;

public class UsuarioNaoEncontradoException extends Exception {
    //Construtor usado para verificar se existe usuário logado
    public UsuarioNaoEncontradoException() {
        super("Não existe usuário logado");
    }

    public UsuarioNaoEncontradoException(Integer id) {
        super(String.format("Usuário %d não encontrado", id));
    }
}
