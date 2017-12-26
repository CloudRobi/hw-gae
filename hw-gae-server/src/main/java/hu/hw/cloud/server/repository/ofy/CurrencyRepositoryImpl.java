/**
 * 
 */
package hu.hw.cloud.server.repository.ofy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Repository;

import com.googlecode.objectify.Key;

import hu.hw.cloud.server.entity.common.Currency;
import hu.hw.cloud.server.repository.CurrencyRepository;

/**
 * @author CR
 *
 */
//@Repository("currencyRepository")
public class CurrencyRepositoryImpl extends CrudRepositoryImpl<Currency> implements CurrencyRepository {
	private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyRepositoryImpl.class.getName());

	public CurrencyRepositoryImpl() {
		super(Currency.class);
	}

	@Override
	public String getAccountId(String id) {
		LOGGER.info("getAccountId->id=" + id);
		Key<Currency> key = getKey(id);
		return key.getParent().getString();
	}

	@Override
	protected Object getParent(Currency entity) {
		return entity.getAccount();
	}
}
