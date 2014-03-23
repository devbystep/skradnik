package by.minsler.skradnik.dao;

/**
 * @author Dzmitry Misiuk
 */
public class DAOException extends Exception{


    public DAOException() {
        super();
    }

    public DAOException(String s) {
        super(s);
    }

    public DAOException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
