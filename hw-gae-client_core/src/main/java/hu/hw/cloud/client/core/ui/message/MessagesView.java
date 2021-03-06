/**
 * 
 */
package hu.hw.cloud.client.core.ui.message;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.inject.Inject;

import com.gwtplatform.mvp.client.ViewImpl;

import hu.hw.cloud.client.core.ui.message.ui.MessageWidget;
import hu.hw.cloud.client.core.ui.message.ui.MessageWidgetFactory;

/**
 * @author CR
 *
 */
public class MessagesView extends ViewImpl implements MessagesPresenter.MyView {
    interface Binder extends UiBinder<Widget, MessagesView> {
    }

    @UiField
    HTMLPanel messages;

    private final MessageWidgetFactory messageWidgetFactory;

    @Inject
    MessagesView(
            Binder binder,
            MessageWidgetFactory messageWidgetFactory) {
        this.messageWidgetFactory = messageWidgetFactory;

        initWidget(binder.createAndBindUi(this));
    }

    @Override
    public void addMessage(Message message) {
        MessageWidget messageWidget = messageWidgetFactory.createMessage(message);
        messages.add(messageWidget);
    }
}