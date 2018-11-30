/**
 * 
 */
package lu.wealins.liability.services.core.validation;

import lu.wealins.common.dto.liability.services.ClientLiteDTO;

/**
 * This interface is responsible of the client validation execution. <br>
 * 
 * @author oro
 *
 */
public interface ClientValidator<T extends ClientLiteDTO> {
	
	/**
	 * Execute the validation of client using the provided service.
	 * 
	 * @param client the client to be validated
	 */
	void validate(T client);
}
