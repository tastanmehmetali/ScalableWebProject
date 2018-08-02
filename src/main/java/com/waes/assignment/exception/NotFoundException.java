package com.waes.assignment.exception;

/**
 * <p>
 * to be used when the entity isn't available for the application process
 * </p>
 * 
 * @author Mehmet Ali Tastan
 */
public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = -5728219364621568581L;

	/**
     * <p>
     * The default constructor for the exception
     * </p>
     *
     * @param message the error message to be passed along for other layers or services
     */
    public NotFoundException(final String message) {
        super(message);
    }
}
