/**
 * 
 */
package hu.hw.cloud.client.fro.configcommon;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

import hu.hw.cloud.client.core.users.UsersTable;

/**
 * @author CR
 *
 */
public class CommonConfigModule extends AbstractPresenterModule {
	@Override
	protected void configure() {
		bindPresenter(CommonConfigPresenter.class, CommonConfigPresenter.MyView.class, CommonConfigView.class,
				CommonConfigPresenter.MyProxy.class);
		
		bind(UsersTable.class);
	}
}
