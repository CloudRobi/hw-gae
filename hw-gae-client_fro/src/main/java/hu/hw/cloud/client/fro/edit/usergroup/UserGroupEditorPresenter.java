/**
 * 
 */
package hu.hw.cloud.client.fro.edit.usergroup;

import javax.inject.Inject;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rest.delegates.client.ResourceDelegate;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

import hu.hw.cloud.client.core.event.RefreshTableEvent;
import hu.hw.cloud.client.core.gin.CustomActionException;
import hu.hw.cloud.client.core.security.CurrentUser;
import hu.hw.cloud.shared.UserGroupResource;
import hu.hw.cloud.shared.dto.EntityPropertyCode;
import hu.hw.cloud.shared.dto.common.UserGroupDto;

/**
 * @author robi
 *
 */
public class UserGroupEditorPresenter extends PresenterWidget<UserGroupEditorPresenter.MyView>
		implements UserGroupEditorUiHandlers {

	public interface MyView extends View, HasUiHandlers<UserGroupEditorUiHandlers> {
		void open(Boolean isNew, UserGroupDto dto);

		void displayError(EntityPropertyCode code, String message);

		void close();
	}

	private final ResourceDelegate<UserGroupResource> resourceDelegate;

	private final CurrentUser currentUser;

	private Boolean isNew;

	@Inject
	UserGroupEditorPresenter(EventBus eventBus, MyView view, ResourceDelegate<UserGroupResource> resourceDelegate,
			CurrentUser currentUser) {
		super(eventBus, view);

		this.resourceDelegate = resourceDelegate;
		this.currentUser = currentUser;

		getView().setUiHandlers(this);
	}

	@Override
	public void create() {
		isNew = true;

		UserGroupDto dto = new UserGroupDto();
		dto.setAccountDto(currentUser.getAppUserDto().getAccountDto());

		getView().open(isNew, dto);
	}

	@Override
	public void edit(UserGroupDto dto) {
		isNew = false;
		getView().open(isNew, dto);
	}

	@Override
	public void save(UserGroupDto dto) {
		if (isNew) {
			createEntity(dto);
		} else {
			updateEntity(dto);
		}
	}

	private void createEntity(UserGroupDto dto) {
		resourceDelegate.withCallback(new AsyncCallback<UserGroupDto>() {
			@Override
			public void onSuccess(UserGroupDto dto) {
				RefreshTableEvent.fire(UserGroupEditorPresenter.this, RefreshTableEvent.TableType.USER_GROUP);
				getView().close();
			}

			@Override
			public void onFailure(Throwable caught) {
				if (caught instanceof CustomActionException) {
					customMessage((CustomActionException) caught);
					return;
				}
				getView().displayError(EntityPropertyCode.USER_GROUP_NAME, caught.getMessage());
			}
		}).create(dto);
	}

	private void updateEntity(UserGroupDto dto) {
		resourceDelegate.withCallback(new AsyncCallback<UserGroupDto>() {
			@Override
			public void onSuccess(UserGroupDto dto) {
				RefreshTableEvent.fire(UserGroupEditorPresenter.this,RefreshTableEvent.TableType.USER_GROUP);
				getView().close();
			}

			@Override
			public void onFailure(Throwable caught) {
				if (caught instanceof CustomActionException) {
					customMessage((CustomActionException) caught);
					return;
				}
				getView().displayError(EntityPropertyCode.USER_GROUP_NAME, caught.getMessage());
			}
		}).update(dto);
	}

	private void customMessage(CustomActionException e) {
		getView().displayError(EntityPropertyCode.USER_GROUP_NAME,
				e.getErDto().getProperty() + "/" + e.getErDto().getExceptionType());
	}
}