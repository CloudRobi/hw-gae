/**
 * 
 */
package hu.hw.cloud.client.kip.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

/**
 * @author robi
 *
 */
public interface KipGssResources extends ClientBundle {
	public static final KipGssResources INSTANCE = GWT.create(KipGssResources.class);

	interface Style extends CssResource {
		String chat_list_view_col_left();
		String chat_list_view_col_right();
		String chat_list_view_collection();
		
		String chat_details_widget();

		String chat_creator_panel();

		String send_message_textbox();
	}

	@Source({"hu/hw/cloud/client/kip/resources/css/kip.gss" })
	Style common();
}
