/**
 * 
 */
package hu.hw.cloud.client.fro.filter.room;

import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;

import gwt.material.design.addins.client.combobox.MaterialComboBox;
import gwt.material.design.client.ui.MaterialChip;

import hu.hw.cloud.client.core.i18n.CoreConstants;
import hu.hw.cloud.client.core.i18n.CoreMessages;
import hu.hw.cloud.client.fro.filter.AbstractFilterView;

/**
 * @author robi
 *
 */
public class RoomFilterView extends AbstractFilterView implements RoomFilterPresenter.MyView {
	private static Logger logger = Logger.getLogger(RoomFilterView.class.getName());

	private MaterialChip floorChip;
	private MaterialComboBox<String> floorCombo;

	@Inject
	RoomFilterView(CoreMessages i18nCore, CoreConstants cnstCore) {
		super(i18nCore);

		initFloorFilter(i18nCore);
	}

	@Override
	protected void initView() {
		super.initView();
	}

	private void initFloorFilter(CoreMessages i18nCore) {
		floorChip = new MaterialChip();

		floorCombo = new MaterialComboBox<String>();
		floorCombo.setAllowClear(true);
		collapsibleBody.add(floorCombo);

		floorCombo.setLabel(i18nCore.roomTypeFilterInventoryTypeLabel());
		floorCombo.setPlaceholder(i18nCore.roomTypeFilterInventoryTypePlaceholder());

		floorCombo.addSelectionHandler(e -> {
			String floorText = null;
			if (e.getSelectedValues().get(0) != null)
				floorText = e.getSelectedValues().get(0);
			setFloorChip(floorText);
			getUiHandlers().filterChange();
		});
		floorCombo.addRemoveItemHandler(e -> {
			setFloorChip(null);
			getUiHandlers().filterChange();
		});
	}

	private void setFloorChip(String type) {
		if (floorChip.isAttached()) {
			if (type == null) {
				collapsibleHeader.remove(floorChip);
				return;
			}
			floorChip.setText(type);
		} else {
			if (type != null) {
				floorChip.setText(type);
				collapsibleHeader.add(floorChip);
			}
		}
	}

	@Override
	public void reset() {
		logger.info("RoomTypeFilterView().reset()");
		setFloorChip(null);
	}

	@Override
	public void setFloors(List<String> floors) {
		if (floors != null)
			floorCombo.addItems(floors);
	}

	@Override
	public String getSelectedFloor() {
		return floorCombo.getSingleValue();
	}
}