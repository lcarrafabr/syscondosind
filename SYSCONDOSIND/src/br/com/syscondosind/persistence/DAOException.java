package br.com.syscondosind.persistence;

/**
 *
 * @author Luciano Carrafa Benfica
 */
public class DAOException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public DAOException(String msg, Throwable t) {
        super(msg, t);
    }
}
