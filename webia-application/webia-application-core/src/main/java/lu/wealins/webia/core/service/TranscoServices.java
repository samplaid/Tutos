package lu.wealins.webia.core.service;

import lu.wealins.webia.ws.rest.request.SearchTranscoRequest;
import lu.wealins.webia.ws.rest.request.SearchTranscoResponse;

/**
 * The NAVs services interface
 * 
 * @author bqv55
 *
 */
public interface TranscoServices {

	SearchTranscoResponse searchTransco(SearchTranscoRequest searchTranscoRequest);

}
