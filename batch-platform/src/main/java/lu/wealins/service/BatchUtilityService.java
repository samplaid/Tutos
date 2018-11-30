/**
 * 
 */
package lu.wealins.service;

import java.util.Date;
import java.util.Map;

import org.springframework.batch.core.scope.context.ChunkContext;

/**
 * @author xqv66
 *
 */
public interface BatchUtilityService {

	/**
	 * Retrieve job parameters from the chunk context.
	 * 
	 * @param chunkContext
	 *            The chunk context.
	 * @return The job parameters.
	 */
	Map<String, Object> getJobParameters(ChunkContext chunkContext);

	/**
	 * Retrieve a String job parameter from the chunk context according its key.
	 * 
	 * @param chunkContext The chunck context.
	 * @param key The key.
	 * @return The string job parameter.
	 */
	String getStringJobParameter(ChunkContext chunkContext, Object key);

	/**
	 * Retrieve a String job parameter from the chunk context according its key.
	 * 
	 * @param chunkContext The chunck context.
	 * @param key The key.
	 * @return The integer job parameter.
	 */
	Integer getIntegerJobParameter(ChunkContext chunkContext, Object key);

	/**
	 * Retrieve a date job parameter from the chunk context according its key.
	 * 
	 * @param chunkContext The chunck context.
	 * @param key The key.
	 * @return The date job parameter.
	 */
	Date getDateJobParameter(ChunkContext chunkContext, Object key);
}
