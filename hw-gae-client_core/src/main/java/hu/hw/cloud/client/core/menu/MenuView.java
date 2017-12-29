/**
 * 
 */
package hu.hw.cloud.client.core.menu;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import gwt.material.design.addins.client.sideprofile.MaterialSideProfile;
import gwt.material.design.client.constants.IconPosition;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialHeader;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialNavBar;
import gwt.material.design.client.ui.MaterialSideNavPush;
import gwt.material.design.client.ui.MaterialToast;
import hu.hw.cloud.client.core.event.ContentPushEvent.MenuState;
import hu.hw.cloud.client.core.resources.CoreResources;
import hu.hw.cloud.shared.cnst.MenuItemType;
import hu.hw.cloud.shared.cnst.SubSystem;
import hu.hw.cloud.shared.dto.core.MenuItemDto;

/**
 * @author CR
 *
 */
public class MenuView extends ViewWithUiHandlers<MenuUiHandlers> implements MenuPresenter.MyView {
	private static Logger logger = Logger.getLogger(MenuView.class.getName());

	interface Binder extends UiBinder<Widget, MenuView> {
	}

	@UiField
	MaterialHeader header;

	@UiField
	HTMLPanel brandPanel;

	@UiField
	MaterialNavBar navBar;

	@UiField
	MaterialSideNavPush sideNav;

	@UiField
	MaterialSideProfile sideProfile;

	@UiField
	SimplePanel navBarSlot;

	private final CoreResources res;
	private List<MaterialLink> singleLinks = new ArrayList<MaterialLink>();
	private List<MaterialCollapsible> collapsibles = new ArrayList<MaterialCollapsible>();

	@Inject
	MenuView(Binder uiBinder, CoreResources res) {
		initWidget(uiBinder.createAndBindUi(this));

		this.res = res;

		sideNav.addOpenedHandler(event -> getUiHandlers().setContentPush(MenuState.OPEN));
		sideNav.addClosedHandler(event -> getUiHandlers().setContentPush(MenuState.CLOSE));
	}

	@Override
	public void setPageTitle(String title, String description) {
		// this.title.setText(title);
		// this.description.setText(description);

		// MaterialAnimator.animate(Transition.BOUNCEINLEFT, this.title, 1000);
		// MaterialAnimator.animate(Transition.BOUNCEINLEFT, this.description,
		// 1000);

	}

	@Override
	public void checkPermittedWidgets() {
		// hkMaterialCollapsibleItem.setVisible(getUiHandlers().canReveal(Permissions.PERM_MENU_HK));
	}

	@Override
	public void setAccountName(String accountName) {
		// profileWidget.setAccountName(accountName);
	}

	@Override
	public void setUserName(String userName) {
		// profileWidget.setUserName(userName);
	}

	@Override
	public void setHotelName(String hotelName) {
		// profileWidget.setHotelName(hotelName);
	}

	@Override
	public void setBusinessDate(Date businessDate) {
		// profileWidget.setBusinessDate(businessDate);
	}

	@UiHandler("accountDropDown")
	void onAccountDropDown(SelectionEvent<Widget> callback) {
		MaterialToast.fireToast("Selected : " + ((MaterialLink) callback.getSelectedItem()).getText());
	}

	@UiHandler("hotelDropDown")
	void onHotelDropDown(SelectionEvent<Widget> callback) {
		MaterialToast.fireToast("Selected : " + ((MaterialLink) callback.getSelectedItem()).getText());
	}

	@UiHandler("userDropDown")
	void onUserDropDown(SelectionEvent<Widget> callback) {
		MaterialToast.fireToast("Selected : " + ((MaterialLink) callback.getSelectedItem()).getText());
	}

	@Override
	public void setInSlot(Object slot, IsWidget content) {
		if (slot == MenuPresenter.SLOT_NAVBAR) {
			navBarSlot.setWidget(content);
		}
	}

	@Override
	public void setMenuItems(List<MenuItemDto> menuItems) {
		MenuItemType prevType = null;

		singleLinks.clear();
		collapsibles.clear();
		MaterialCollapsible collapsible = null;

		for (MenuItemDto menuItem : menuItems) {
			logger.log(Level.INFO, "MenuView.setMenuItems()->menuItem=" + menuItem.getText());
			if (menuItem.getType().equals(MenuItemType.MENU_ITEM))
				sideNav.add(createMenuItem(menuItem));

			if (menuItem.getType().equals(MenuItemType.SUB_MENU)) {
				if ((prevType == null) || (prevType.equals(MenuItemType.MENU_ITEM))) {
					collapsible = new MaterialCollapsible();
					collapsibles.add(collapsible);
					sideNav.add(collapsible);
				}
				collapsible.add(new SubMenuWidget(menuItem));
			}
			prevType = menuItem.getType();
		}
	}

	private MaterialLink createMenuItem(MenuItemDto menuItem) {
		MaterialLink link = new MaterialLink();
		link.setIconPosition(IconPosition.LEFT);
		if (menuItem.getIcon() != null)
			link.setIconType(IconType.valueOf(menuItem.getIcon()));
		link.setText(menuItem.getText());
		link.setTargetHistoryToken(menuItem.getNameToken());
		singleLinks.add(link);
		return link;
	}

	@Override
	public void inactivateSingleLinks() {
		for (MaterialLink link : singleLinks)
			link.getParent().removeStyleName("active");
	}

	@Override
	public void closeCollapisbles() {
		for (MaterialCollapsible collapsible : collapsibles)
			collapsible.closeAll();
	}

	@Override
	public void setAppCode(String appCode) {
		switch (appCode) {
		case SubSystem.INF:
			sideProfile.setResource(res.orangeWallpaperImg());
			brandPanel.add(new HTML("HostWare <span>Cloud</span> " + appCode));
			break;
		case SubSystem.KIP:
			sideProfile.setResource(res.blueWallpaperImg());
			brandPanel.add(new HTML("HostWare <span>Cloud</span> " + appCode));
			break;
		case SubSystem.FRO:
			sideProfile.setResource(res.redWallpaperImg());
			brandPanel.add(new HTML("HostWare <span>Cloud</span> " + appCode));
			break;
		}

	}
}