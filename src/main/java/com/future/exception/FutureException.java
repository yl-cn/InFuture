package com.future.exception;

public class FutureException extends Exception {

    private static final long serialVersionUID = 1748413000000789156L;

    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * Constructors.
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */

    /**
     * Constructs an {@code FutureException} with {@code null}
     * as its error detail message.
     */
    public FutureException() {
        super();
    }

    /**
     * Constructs an {@code FutureException} with the specified detail message.
     *
     * @param message The detail message (which is saved for later retrieval
     *                by the {@link #getMessage()} method)
     */
    public FutureException(String message) {
        super(message);
    }

    /**
     * Constructs an {@code FutureException} with the specified detail message
     * and cause.
     * <p>
     * <p> Note that the detail message associated with {@code cause} is
     * <i>not</i> automatically incorporated into this exception's detail
     * message.
     *
     * @param message The detail message (which is saved for later retrieval
     *                by the {@link #getMessage()} method)
     * @param cause   The cause (which is saved for later retrieval by the
     *                {@link #getCause()} method).  (A null value is permitted,
     *                and indicates that the cause is nonexistent or unknown.)
     * @since 1.6
     */
    public FutureException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs an {@code FutureException} with the specified cause and a
     * detail message of {@code (cause==null ? null : cause.toString())}
     * (which typically contains the class and detail message of {@code cause}).
     * This constructor is useful for IO exceptions that are little more
     * than wrappers for other throwables.
     *
     * @param cause The cause (which is saved for later retrieval by the
     *              {@link #getCause()} method).  (A null value is permitted,
     *              and indicates that the cause is nonexistent or unknown.)
     * @since 1.6
     */
    public FutureException(Throwable cause) {
        super(cause);
    }


    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * Interface.
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */

    /**
     * <p>
     * Return the exception that is the underlying cause of this exception.
     * </p>
     * <p>
     * <p>
     * This may be used to find more detail about the cause of the error.
     * </p>
     *
     * @return the underlying exception, or <code>null</code> if there is not
     * one.
     */
    public Throwable getUnderlyingException() {
        return super.getCause();
    }

    @Override
    public String toString() {
        Throwable cause = getUnderlyingException();
        if (cause == null || cause == this) {
            return super.toString();
        } else {
            return super.toString() + " [See nested exception: " + cause + "]";
        }
    }
}
