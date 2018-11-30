package lu.wealins.common.nas;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * Represents the URL of a NAS resource.<br/>
 * A NAS URL is composed of tree parts :
 * <ul>
 * <li>the NAS protocol prefix</li>
 * <li>the logical name of the NAS referred as nasName</li>
 * <li>and an optional resource path referred as resourcePath</li>
 * </ul>
 * </p>
 * 
 * <p>
 * The following representations are valid :
 * <ul>
 * <li><code>nas://nasname/resourcepath</code></li>
 * <li><code>nas://nasname/resourcepath/</code></li>
 * <li><code>nas://nasname/</code></li>
 * <li><code>nas://nasname</code></li>
 * </ul>
 * </p>
 * </p>
 * 
 * <h4>NAS protocol prefix</h4>
 * <p>
 * The NAS protocol prefix is nas://. The implementation will accept and normalize backslashes. A string cannot represent a NAS URL if it doesn't start with the NAS protocol prefix.
 * </p>
 * 
 * <h4>NAS name part</h4>
 * <p>
 * It's the logical name of the NAS. It is independent of the physical mount point of the NAS. The {@link NasURLResolver} can convert the NasURL to the physical location.
 * </p>
 * 
 * <h4>Resource path part</h4>
 * <p>
 * The resourcePath denotes the localization of the resource within the NAS. It's relative to the NAS mount point. When the resourcePath is not provided, the NasURL represents the mount point itself.
 * </p>
 * 
 * @see NasURLResolver
 */
public class NasURL {

	public static final String URL_NAS_PROTOCOL_PREFIX = "nas://";
	public static final String URL_SEPARATOR = "/";

	/**
	 * Regular expression for separators.<br/>
	 * Accepts slash and backslash.
	 */
	private static final String REGEX_SEPARATOR = "\\\\/";

	/**
	 * Regular expression for valid NAS protocol prefix.<br/>
	 * Accepts lower case nas followed by two separators. It can be <code>nas://</code> or <code>nas:\\</code> or even <code>nas:/\</code> and so on.
	 */
	private static final String REGEX_PROTOCOL = "nas:[" + REGEX_SEPARATOR + "]{2}";

	/**
	 * Regular expression for valid NAS logical names.<br/>
	 * Accepts only letters and digits. Whitespace are not accepted. Must be at least one character long.<br/>
	 */
	private static final String REGEX_NASNAME = "([\\w]+)";

	/**
	 * Regular expression for valid characters in resource path.<br/>
	 * Anything except a separator.
	 */
	private static final String REGEX_RESOURCE_CHARACTER = "[^" + REGEX_SEPARATOR + "]";

	/**
	 * Regular expression for valid resource path.<br/>
	 */
	private static final String REGEX_RESOURCEPATH = REGEX_RESOURCE_CHARACTER + "([" + REGEX_SEPARATOR + REGEX_RESOURCE_CHARACTER + "])*";

	/**
	 * Basically starts with the protocol then the nasName. Can be followed by a resource path only if there is a separator after the NAS logical name.
	 */
	private static final Pattern NAS_PATTERN = Pattern.compile(REGEX_PROTOCOL + REGEX_NASNAME + "([" + REGEX_SEPARATOR + "]+(" + REGEX_RESOURCEPATH + ")?)?");

	/**
	 * Pattern to check the NAS name.
	 */
	private static final Pattern NASNAME_PATTERN = Pattern.compile(REGEX_NASNAME);
	/**
	 * Pattern to check the resource path.
	 */
	private static final Pattern RESOURCEPATH_PATTERN = Pattern.compile(REGEX_RESOURCEPATH);

	/**
	 * Pattern to find all the separators (of a resource path).
	 */
	private static final Pattern SEPARATOR_PATTERN = Pattern.compile("[" + REGEX_SEPARATOR + "]+");

	/**
	 * Pattern to find the leading separator (of a resource path).
	 */
	private static final Pattern LEADING_SEPARATOR_PATTERN = Pattern.compile("^[" + REGEX_SEPARATOR + "]");

	private final String nasName;
	private final String resourcePath;

	/**
	 * Constructs a nasURL from the given string.
	 * 
	 * @param nasURL string representing the resource on a NAS
	 * @throws MalformedNasURLException if the provided string is not valid
	 */
	public NasURL(String nasURL) throws MalformedNasURLException {
		if (nasURL == null) {
			throw new MalformedNasURLException("nasURL must be not null");
		}

		// Extract nasName and resourcePath from URL
		Matcher nasMatcher = NAS_PATTERN.matcher(nasURL);
		boolean valid = nasMatcher.matches();
		if (!valid) {
			throw new MalformedNasURLException("Invalid nasURL '" + nasURL + "'");
		}

		String groupNasName = nasMatcher.group(1);
		String groupResourcePath = nasMatcher.group(2);

		// Handle nasName
		buildValidNasName(groupNasName);
		this.nasName = groupNasName;

		// Handle resourcePath
		try {
			groupResourcePath = buildValidResourcePath(groupResourcePath);
		} catch (MalformedNasURLException e) {
			throw new MalformedNasURLException("Invalid nasURL '" + nasURL + "'. " + e.getMessage(), e);
		}
		this.resourcePath = groupResourcePath;
	}

	/**
	 * Builds a valid NAS name from the given one.
	 * 
	 * @param nasName nasName to be verified
	 * @return a valid nasName
	 * @throws MalformedNasURLException if the nasName is not valid.
	 */
	private static String buildValidNasName(String nasName) throws MalformedNasURLException {
		String nn = nasName == null ? "" : nasName;

		if (nn.trim().length() == 0) {
			throw new MalformedNasURLException("Invalid nasName '" + nasName + "'");
		}
		Matcher m = NASNAME_PATTERN.matcher(nn);
		if (!m.matches()) {
			throw new MalformedNasURLException("Invalid nasName '" + nasName + "'");
		}
		return nn;
	}

	/**
	 * Builds a valid and normalized resource path from the given one.<br/>
	 * The given resource path is not a part of the NAS resource path. It is the whole resource path of a NAS URL.<br/>
	 * If the resource path is not absolute, a {@link MalformedNasURLException} is thrown. Because such a NasURL 'nas://nasname/../somewhere' would locate the resource outside of the scope of the NAS,
	 * which is not allowed.
	 * 
	 * @param resourcePath resourcePath to be verified and normalized
	 * @return a valid normalized resource path
	 * @throws MalformedNasURLException if the resourcePath is not valid.
	 */
	private static String buildValidResourcePath(String resourcePath) throws MalformedNasURLException {
		String rp = (resourcePath == null ? "" : resourcePath.trim());

		// normalize separators
		// change all to URL_SEPARATOR, remove doubles, remove leading one if any
		rp = SEPARATOR_PATTERN.matcher(rp).replaceAll(URL_SEPARATOR);
		rp = LEADING_SEPARATOR_PATTERN.matcher(rp).replaceAll("");

		// empty resource path
		if (rp.length() == 0) {
			return rp;
		}

		// check compliance with RegEx
		Matcher m = RESOURCEPATH_PATTERN.matcher(rp);
		if (!m.matches()) {
			throw new MalformedNasURLException("Invalid resourcePath '" + resourcePath + "'");
		}

		// normalize relative
		rp = normalizeRelativeResourcePath(rp);
		// If the resolved path starts with '..', it means the resource path is not absolute.
		// Resource path must stay within the NAS scope, so throw an exception.
		if (rp.startsWith("..")) {
			throw new MalformedNasURLException("Invalid resource path '" + resourcePath + "'. It refers to a resource outside of the mount point.");
		}
		return rp;

	}

	/**
	 * Normalizes the resource path<br/>
	 * It makes the resource path absolute dealing with '..' and '.'.
	 * 
	 * @param resourcePath resourcePath to normalize
	 * @return a normalized resourcePath
	 * @throws MalformedNasURLException MalformedNasURLException if the normalization fails
	 */
	private static String normalizeRelativeResourcePath(String resourcePath) throws MalformedNasURLException {
		String rPath = resourcePath;
		try {
			// Create a URI without an authority
			// Reason: NAS name may not comply URI authority rule
			URI resourcePathURI = new URI(null, null, rPath, null);

			// Create a base URI symbolizing the root in order to normalize the resource path
			// against it.
			URI nasURI = new URI(null, null, null, null);
			rPath = nasURI.resolve(resourcePathURI).getPath();

			return rPath;
		} catch (URISyntaxException e) {
			throw new MalformedNasURLException("Invalid resource path '" + resourcePath + "'. " + e.getMessage(), e);
		}
	}

	/**
	 * Constructs a new NasURL. The new NasURL has the following properties :<br/>
	 * <ul>
	 * <li>Its logical name is identical to this nasURL</li>
	 * <li>Its resource path is the concatenation of first this nasURL resource path and then the given appendResourcePath</li>
	 * </ul>
	 * 
	 * @param appendResourcePath the resource path to append
	 * @return a new NasURL with the appended resource path
	 * @throws MalformedNasURLException if the appendResourcePath would lead to a malformed NasURL
	 */
	public NasURL appendResourcePath(String appendResourcePath) throws MalformedNasURLException {
		String newNasName = getNasName();
		String newResourcePath = getResourcePath();

		// build the new resource path by appending the given string to the existing resource path.
		if (appendResourcePath != null && appendResourcePath.trim().length() > 0) {
			newResourcePath += URL_SEPARATOR + appendResourcePath;
		}
		try {
			return new NasURL(newNasName, newResourcePath);
		} catch (MalformedNasURLException e) {
			throw new MalformedNasURLException("Invalid appendResourcePath '" + appendResourcePath + "'. " + e.getMessage(), e);
		}
	}

	/**
	 * Gets the resource path of this NasURL relative to the given part.<br/>
	 * For example, in 'nas://nasname/folder/test/file.pdf' the relative resource path from 'folder' is 'test/file.pdf'
	 * 
	 * @param from the resource path from which the build the relative part
	 * @return the relative resource path
	 * @throws MalformedNasURLException if the relative resource path cannot be determined
	 */
	public String getRelativeResourcePath(String from) throws MalformedNasURLException {
		try {
			// normalize the from resourcePath
			String fromPath = normalizeRelativeResourcePath(from);

			// normalized form of from resourcePath is empty, the relative resourcePath is the
			// resourcePath itself.
			if (fromPath.equals("")) {
				return resourcePath;
			}

			// calculate relativePath of the resource path against the from resource path
			URI resourcePathURI = new URI(null, null, resourcePath, null);
			URI fromURI = new URI(null, null, fromPath, null);
			URI relativeURI = fromURI.relativize(resourcePathURI);
			// JavaDoc of URI states that if the path of this URI is not a prefix of the path of the
			// given URI, then the given URI is returned, so we test for identity (not equals).
			if (relativeURI == resourcePathURI) {
				// same object
				throw new MalformedNasURLException("Cannot extract the relative resource path of '" + this.normalizeString() + "' from '" + from + "'.");
			}

			return relativeURI.getPath();
		} catch (URISyntaxException e) {
			throw new MalformedNasURLException("Cannot extract the relative resource path of '" + this.normalizeString() + "' from '" + from + "'." + e.getMessage(), e);
		}
	}

	/**
	 * Constructs a NasURL with the given nasName and resourcePath.
	 * 
	 * @param nasName the logical name of the NAS of the resource
	 * @param resourcePath the path of the resource relative to the NAS mount point
	 * @throws MalformedNasURLException if the provided string is not valid
	 */
	public NasURL(String nasName, String resourcePath) throws MalformedNasURLException {
		this.nasName = buildValidNasName(nasName);
		this.resourcePath = buildValidResourcePath(resourcePath);
	}

	/**
	 * Determine whether the given string is a valid NAS URL.
	 * 
	 * @param nasURL the string to validate
	 * @return true if the given string is a valid NAS URL, otherwise false.
	 */
	public static boolean isNasURL(String nasURL) {
		try {
			new NasURL(nasURL);
			return true;
		} catch (MalformedNasURLException e) {
			return false;
		}
	}

	/**
	 * Determine whether the given string is a valid nasName.
	 * 
	 * @param nasName the string to validate
	 * @return true if the given string is a valid nasName, otherwise false.
	 */
	public static boolean isValidNasName(String nasName) {
		try {
			buildValidNasName(nasName);
			return true;
		} catch (MalformedNasURLException e) {
			return false;
		}
	}

	/**
	 * Determine whether the given string is a valid resource path to be appended.<br/>
	 * A valid resource path cannot go outside the boundaries of the logical NasURL. For example, in the context of the following NasURL nas://nasname/folder :<br/>
	 * <ul>
	 * <li>file.pdf is a valid resource path</li>
	 * <li>../file.pdf is valid resource path</li>
	 * <li>../../file.pdf is not valid.</li>
	 * </ul>
	 * 
	 * @param appendResourcePath the string to validate
	 * @return true if the given string is a valid nasName, otherwise false.
	 */
	public boolean isValidAppendResourcePath(String appendResourcePath) {
		try {
			this.appendResourcePath(appendResourcePath);
			return true;
		} catch (MalformedNasURLException e) {
			return false;
		}
	}

	/**
	 * Builds a standard NAS URL string representation.
	 * 
	 * @return standard NAS URL as a string
	 */
	public String normalizeString() {
		return URL_NAS_PROTOCOL_PREFIX + nasName + URL_SEPARATOR + resourcePath;
	}

	/**
	 * Returns a string representation of the object.<br/>
	 * User should prefer the {@link #normalizeString()} method if a normalized NAS URL is wanted.
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return normalizeString();
	}

	/**
	 * Get the NAS name of this NasURL.
	 * 
	 * @return the NAS name
	 */
	public String getNasName() {
		return nasName;
	}

	/**
	 * Get the resource path of this NasURL.
	 * 
	 * @return the resource path
	 */
	public String getResourcePath() {
		return resourcePath;
	}

}
