package lu.wealins.common.nas;

import java.io.File;
import java.util.Collection;

/**
 * <p>
 * Converts a {@link NasURL} representation of a resource to a {@link File} representation of the same resource. Basically, it must translate the nasName part of the NasURL to the mount point of the
 * NAS.<br/>
 * It doesn't check the existence a the File.
 * </p>
 * 
 * @see NasURL
 */
public interface NasURLResolver {

	/**
	 * Resolves a nasURL representation of a resource to a File representation.
	 * 
	 * @param nasURL the nasURL to resolve
	 * @return the File representation of the nasURL
	 * @throws NasURLResolverException if the nasURL cannot be resolved
	 */
	File resolve(NasURL nasURL) throws NasURLResolverException;

	/**
	 * Convenience method, does the same as {@link NasURLResolver#resolve(NasURL)}
	 * 
	 * @see NasURLResolver#resolve(NasURL).
	 * @param nasURL the nasURL to resolve given as a string
	 * @return the File representation of the nasURL
	 * @throws MalformedNasURLException if the nasURL string is not a valid nasURL
	 * @throws NasURLResolverException if the nasURL cannot be resolved
	 */
	File resolve(String nasURL) throws MalformedNasURLException, NasURLResolverException;

	/**
	 * Determine whether the given nasURL can be resolved by this resolver.
	 * 
	 * @param nasURL nasURL to resolve
	 * @return true if the nasURL can be resolved by this resolver
	 * @throws NasURLResolverException if there is an problem while trying to determine whether the nasURL can be resolved
	 */
	boolean canResolve(NasURL nasURL) throws NasURLResolverException;

	/**
	 * Determine whether the given string representing the nasName part of the nasURL can be resolved by this resolver.
	 * 
	 * @param nasURL a string representing a nasURL to resolve
	 * @return true if the nasURL can be resolved by this resolver, otherwise false
	 * @throws MalformedNasURLException if the nasURL string is not a valid nasURL
	 * @throws NasURLResolverException if there is an problem while trying to determine whether the nasURL can be resolved
	 */
	boolean canResolve(String nasURL) throws NasURLResolverException, MalformedNasURLException;

	/**
	 * Gets the list of the NAS names known by the NasURLResolver.
	 * 
	 * @return the nasNames known by the NasURLResolver
	 * @throws NasURLResolverException if the nasNames cannot be determined
	 */
	Collection<String> getNasNames() throws NasURLResolverException;

	/**
	 * Determine the nasName corresponding to the given mountPoint.
	 * 
	 * @param mountPoint the mountPoint
	 * @return the nasName or null if there is no nasName associated to the given mountPoint
	 * @throws NasURLResolverException if the nasName cannot be determined
	 */
	String getNasName(File mountPoint) throws NasURLResolverException;

	/**
	 * Determine the mountPoint corresponding to the given nasName
	 * 
	 * @param nasName the nasName to get the mountPoint of.
	 * @return the mountPoint corresponding to this nasName, null if there is no mountPiont associated to the given nasName
	 * @throws NasURLResolverException
	 */
	File getMountPoint(String nasName) throws NasURLResolverException;

	/**
	 * Register the mountPoint of the given nasName.
	 * 
	 * @param nasName the nasName of the mountPoint
	 * @param mountPoint the mountPoint of the nasName
	 * @throws NasURLResolverException if the registration fails
	 */
	void addNasMountPoint(String nasName, File mountPoint) throws NasURLResolverException;

}
