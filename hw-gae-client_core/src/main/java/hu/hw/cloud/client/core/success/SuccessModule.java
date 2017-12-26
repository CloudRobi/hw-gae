/**
 * 
 */
package hu.hw.cloud.client.core.success;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

/**
 * @author CR
 *
 */
public class SuccessModule extends AbstractPresenterModule {
	@Override
	protected void configure() {
		bindPresenter(SuccessPresenter.class, SuccessPresenter.MyView.class, SuccessView.class,
				SuccessPresenter.MyProxy.class);
	}
}
