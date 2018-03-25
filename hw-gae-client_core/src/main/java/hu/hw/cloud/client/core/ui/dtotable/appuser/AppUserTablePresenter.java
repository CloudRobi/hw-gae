/**
 * 
 */
package hu.hw.cloud.client.core.ui.dtotable.appuser;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rest.delegates.client.ResourceDelegate;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.presenter.slots.SingleSlot;

import hu.hw.cloud.client.core.security.CurrentUser;
import hu.hw.cloud.client.core.ui.dtotable.AbstractTablePresenter;
import hu.hw.cloud.client.core.ui.editor.DtoEditorFactory;
import hu.hw.cloud.client.core.ui.editor.appuser.AppUserEditPresenter;
import hu.hw.cloud.client.core.util.AbstractAsyncCallback;
import hu.hw.cloud.client.core.util.ErrorHandlerAsyncCallback;
import hu.hw.cloud.shared.AppUserResource;
import hu.hw.cloud.shared.dto.common.AppUserDto;

/**
 * @author robi
 *
 */
public class AppUserTablePresenter extends AbstractTablePresenter<AppUserTablePresenter.MyView>
		implements AppUserTableUiHandlers {
	private static Logger logger = Logger.getLogger(AppUserTablePresenter.class.getName());

	public interface MyView extends View, HasUiHandlers<AppUserTableUiHandlers> {
		void setData(List<AppUserDto> data);
	}

	public static final SingleSlot<PresenterWidget<?>> SLOT_EDITOR = new SingleSlot<>();

	private final ResourceDelegate<AppUserResource> resourceDelegate;

	private final AppUserEditPresenter editor;

	@Inject
	AppUserTablePresenter(EventBus eventBus, MyView view, ResourceDelegate<AppUserResource> userResourceDelegate,
			CurrentUser currentUser, DtoEditorFactory dtoEditorFactory) {
		super(eventBus, view);

		this.resourceDelegate = userResourceDelegate;
		this.editor = dtoEditorFactory.createAppUserEditor();

		getView().setUiHandlers(this);
	}

	@Override
	protected void onBind() {
		super.onBind();
		setInSlot(SLOT_EDITOR, editor);
	}

	@Override
	protected void loadData() {
		logger.log(Level.INFO, "loadData()");
		resourceDelegate.withCallback(new AbstractAsyncCallback<List<AppUserDto>>() {
			@Override
			public void onSuccess(List<AppUserDto> result) {
				logger.log(Level.INFO, "loadData().onSuccess");
				getView().setData(result);
			}
		}).list();
	}

	@Override
	public void addItem() {
		logger.log(Level.INFO, "addItem()");
		editor.create();
	}

	@Override
	public void editItem(AppUserDto dto) {
		editor.edit(dto);
	}

	@Override
	public void deleteItem(AppUserDto dto) {
		deleteData(dto.getWebSafeKey());
	}

	private void deleteData(String webSafeKey) {
		resourceDelegate.withCallback(new AbstractAsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				loadData();
			}
		}).delete(webSafeKey);
	}

	@Override
	public void inviteItem(AppUserDto dto) {
		resourceDelegate.withCallback(new ErrorHandlerAsyncCallback<AppUserDto>(this) {
			@Override
			public void onSuccess(AppUserDto userDto) {
			}

			@Override
			public void onFailure(Throwable caught) {
			}
		}).invite(dto);
	}

	@Override
	public void clearFcmTokens(AppUserDto dto) {
		dto.getFcmTokenDtos().clear();
		resourceDelegate.withCallback(new ErrorHandlerAsyncCallback<AppUserDto>(this) {
			@Override
			public void onSuccess(AppUserDto userDto) {
			}

			@Override
			public void onFailure(Throwable caught) {
			}
		}).update(dto);
	}
}