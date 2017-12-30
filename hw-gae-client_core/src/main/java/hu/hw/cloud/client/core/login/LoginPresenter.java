package hu.hw.cloud.client.core.login;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import com.google.common.base.Strings;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rest.client.RestDispatch;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.NoGatekeeper;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;

import hu.hw.cloud.client.core.CoreNameTokens;
import hu.hw.cloud.client.core.security.AppData;
import hu.hw.cloud.client.core.security.CurrentUser;
import hu.hw.cloud.shared.AuthService;
import hu.hw.cloud.shared.dto.common.AppUserDto;

public class LoginPresenter extends Presenter<LoginPresenter.MyView, LoginPresenter.MyProxy>
		implements LoginUiHandlers {
	private static Logger logger = Logger.getLogger(LoginPresenter.class.getName());

	private static final String ACCOUNT_ID = "accountId";

	interface MyView extends View, HasUiHandlers<LoginUiHandlers> {
		void setAccountId(String accountId);

		void setAppCode(String appCode);
	}

	@NameToken(CoreNameTokens.LOGIN)
	@ProxyStandard
	@NoGatekeeper
	interface MyProxy extends ProxyPlace<LoginPresenter> {
	}

	private final PlaceManager placeManager;
	private final RestDispatch dispatcher;
	private final AuthService authService;
	private final AppData appData;
	private final CurrentUser currentUser;

	private String placeToGo;

	@Inject
	LoginPresenter(EventBus eventBus, MyView view, MyProxy proxy, PlaceManager placeManager, RestDispatch dispatcher,
			AuthService authService, AppData appData, CurrentUser currentUser) {
		super(eventBus, view, proxy, RevealType.Root);
		logger.log(Level.INFO, "LoginPresenter()");

		this.placeManager = placeManager;
		this.dispatcher = dispatcher;
		this.authService = authService;
		this.appData = appData;
		this.currentUser = currentUser;

		getView().setUiHandlers(this);
	}

	@Override
	public void prepareFromRequest(PlaceRequest request) {
		placeToGo = request.getParameter("placeToGo", null);

		checkCurentUser();
	}

	@Override
	protected void onBind() {
		super.onBind();
		if (appData.getAppCode() != null)
			getView().setAppCode(appData.getAppCode());
	}

	@Override
	public boolean useManualReveal() {
		return true;
	}

	@Override
	protected void onReveal() {
		super.onReveal();
		getView().setAccountId(Cookies.getCookie(ACCOUNT_ID));
	}

	/**
	 * 
	 */
	private void checkCurentUser() {
		dispatcher.execute(authService.isCurrentUserLoggedIn(), new AsyncCallback<Boolean>() {

			@Override
			public void onSuccess(Boolean result) {
				if (result) {
					logger.log(Level.INFO, "LoginPresenter().onSuccess().result="+result);
					onSuccessLogin(false);
				} else {
					getProxy().manualReveal(LoginPresenter.this);
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				getProxy().manualReveal(LoginPresenter.this);
			}
		});
	}

	@Override
	public void login(String accoountId, String username, String password, Boolean remeberMe) {
		username = username + ":" + accoountId;
		sendLoginRequest(username, password, remeberMe);
	}

	/**
	 * 
	 * @param username
	 * @param password
	 * @param remeberMe
	 */
	private void sendLoginRequest(String username, String password, Boolean remeberMe) {
		dispatcher.execute(authService.login(username, password, remeberMe), new AsyncCallback<Void>() {

			@Override
			public void onSuccess(Void result) {
				onSuccessLogin(true);
			}

			@Override
			public void onFailure(Throwable caught) {
			}
		});
	}

	/**
	 * 
	 * @param isLogin
	 */
	private void onSuccessLogin(Boolean isLogin) {
		dispatcher.execute(authService.getCurrentUser(), new AsyncCallback<AppUserDto>() {

			@Override
			public void onSuccess(AppUserDto result) {
				Cookies.setCookie(ACCOUNT_ID, result.getAccountDto().getId().toString());
				currentUser.setLoggedIn(true);
				currentUser.setAppUserDto(result);
				currentUser.setCurrentHotelDto(result.getDefaultHotelDto());

				if (Strings.isNullOrEmpty(placeToGo)) {
					goToPlace(CoreNameTokens.HOME);
				} else {
					goToPlace(placeToGo);
				}

				if (!isLogin)
					getProxy().manualRevealFailed();
			}

			@Override
			public void onFailure(Throwable caught) {
				if (!isLogin)
					getProxy().manualRevealFailed();
			}
		});
	}

	/**
	 * 
	 * @param place
	 */
	private void goToPlace(String place) {
		PlaceRequest placeRequest = new PlaceRequest.Builder().nameToken(place).build();
		placeManager.revealPlace(placeRequest);
	}
}
