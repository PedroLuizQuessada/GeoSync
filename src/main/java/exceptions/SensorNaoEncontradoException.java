package exceptions;

public class SensorNaoEncontradoException extends Exception {
    public SensorNaoEncontradoException(Integer id) {
        super(String.format("Sensor %d não encontrado", id));
    }
}
