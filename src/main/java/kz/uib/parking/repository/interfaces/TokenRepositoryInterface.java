package kz.uib.parking.repository.interfaces;

import kz.uib.parking.model.Token;

/**
 * @author Sanzhar Aubakirov (c0rp.aubakirov@gmail.com)
 */
public interface TokenRepositoryInterface extends RepositoryInterface<Token> {

    public Token readTokenByUserId(final String userId);

    public void removeTokenByUserId(final String userId);
}
