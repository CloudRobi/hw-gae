package hu.hw.cloud.client.fro.editor.hotel;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class HotelEditorModule extends AbstractPresenterModule {

	@Override
	protected void configure() {
		bindPresenter(HotelEditorPresenter.class, HotelEditorPresenter.MyView.class, HotelEditorView.class,
				HotelEditorPresenter.MyProxy.class);
	}
}
