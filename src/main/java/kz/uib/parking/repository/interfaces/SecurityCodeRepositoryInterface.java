package kz.uib.parking.repository.interfaces;

import kz.uib.parking.model.SecurityCode;
import kz.uib.parking.model.User;

/**
 * @author Sanzhar Aubakirov (c0rp.aubakirov@gmail.com)
 */
public interface SecurityCodeRepositoryInterface extends RepositoryInterface<SecurityCode>{

    SecurityCode getBySecurityCode(final String securityCode);

    SecurityCode getByUserId(final String userId);

    Boolean checkIfUserSecurityCode(final String securityCode, final String userId);
}
