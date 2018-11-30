/**
 * 
 */
package lu.wealins.liability.services.core.utils;

import org.springframework.stereotype.Component;

import lu.wealins.liability.services.core.persistence.entity.PolicyEntity;

/**
 * @author HOG
 *
 */
@Component
public class PolicyUtils {

	public boolean isPolicyActive(PolicyEntity p) {
		boolean isActive = false;
		if ((p.getStatus() == 1 && p.getSubStatus() == 1)
				|| (p.getStatus() == 1 && p.getSubStatus() == 3)
				|| (p.getStatus() == 2 && p.getSubStatus() == 2)
				|| (p.getStatus() == 3 && p.getSubStatus() == 1)
				|| (p.getStatus() == 3 && p.getSubStatus() == 2)
				|| (p.getStatus() == 4 && p.getSubStatus() == 1)) {
			isActive = true;
		}

		return isActive;
	}
}
