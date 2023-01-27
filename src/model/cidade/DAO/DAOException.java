package model.cidade.DAO;

public class DAOException extends RuntimeException{

    public DAOException(String message, Exception cause) {
        super(message, cause);
    }
}
