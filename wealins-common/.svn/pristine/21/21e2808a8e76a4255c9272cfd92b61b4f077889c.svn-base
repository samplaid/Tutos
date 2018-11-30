package lu.wealins.common.nas;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * The {@link NasURLResolverImpl} is an implementation of {@link NasURLResolver}. Its resolves NasURL according to a map of correspondence between NAS name part of NasURL and a {@link File}. The file
 * is the mount point of the NAS identified by its logical nasName.
 * </p>
 * 
 */
public class NasURLResolverImpl implements NasURLResolver {

	private Map<String, File> nasMountPoints;

	@Override
	public File resolve(NasURL nasURL) throws NasURLResolverException {
		if (nasURL == null) {
			throw new NasURLResolverException("Cannot resolve a null NasURL");
		}
		if (nasMountPoints == null) {
			throw new NasURLResolverException("No NAS mount point is defined in this resolver.");
		}
		String nasName = nasURL.getNasName();
		File mountPoint = nasMountPoints.get(nasName);
		if (mountPoint == null) {
			throw new NasURLResolverException("No NAS mount point is defined for the NAS logical name '" + nasName + "'");
		}
		return new File(mountPoint, nasURL.getResourcePath());
	}

	@Override
	public File resolve(String nasURL) throws MalformedNasURLException, NasURLResolverException {
		return resolve(new NasURL(nasURL));
	}

	@Override
	public boolean canResolve(NasURL nasURL) {
		if (nasURL != null && nasMountPoints != null) {
			String nasName = nasURL.getNasName();
			return nasMountPoints.containsKey(nasName);
		}
		return false;
	}

	@Override
	public boolean canResolve(String nasURL) throws MalformedNasURLException {
		return canResolve(new NasURL(nasURL));
	}

	/**
	 * Copies each entry of the given map in the resolver.<br/>
	 * Subsequent modification to the given map will have to effect on the resolver.<br/>
	 * 
	 * @param newNasMountPoints map of logical NAS name and corresponding mount point.
	 * @throws IllegalArgumentException if an entry is invalid (invalid logical NAS name or invalid mount point)
	 */
	public void setNasMountPoints(Map<String, File> newNasMountPoints) {
		if (newNasMountPoints == null || newNasMountPoints.isEmpty()) {
			throw new IllegalArgumentException("The provided NAS mount point configuration is empty");
		}
		if (this.nasMountPoints == null) {
			this.nasMountPoints = new HashMap<String, File>();
		} else {
			this.nasMountPoints.clear();
		}
		for (Map.Entry<String, File> entry : newNasMountPoints.entrySet()) {
			addNasMountPoint(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public void addNasMountPoint(String nasName, File mountPoint) {
		validateNasName(nasName);
		if (nasMountPoints == null) {
			nasMountPoints = new HashMap<String, File>();
		}
		nasMountPoints.put(nasName, mountPoint);
	}

	@Override
	public String getNasName(File mountPoint) {
		for (Map.Entry<String, File> entry : nasMountPoints.entrySet()) {
			if (entry.getValue().equals(mountPoint)) {
				return entry.getKey();
			}
		}
		return null;
	}

	@Override
	public Collection<String> getNasNames() {
		Collection<String> nasNames = new ArrayList<String>();
		if (nasMountPoints != null) {
			for (String nasName : nasMountPoints.keySet()) {
				nasNames.add(nasName);
			}
		}
		return nasNames;
	}

	@Override
	public File getMountPoint(String nasName) {
		return nasMountPoints.get(nasName);
	}

	private void validateNasName(String nasName) {
		if (!NasURL.isValidNasName(nasName)) {
			throw new IllegalArgumentException("Logical NAS name '" + nasName + "' is invalid");
		}
	}

}
