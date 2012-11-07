package cc.explain.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * User: kzimnick
 * Date: 04.11.12
 * Time: 14:13
 */
public class UserLoggedOutEvent extends GwtEvent<UserLoggedEventHandler> {

    public static Type<UserLoggedEventHandler> TYPE = new Type<UserLoggedEventHandler>();

    @Override
    public Type<UserLoggedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(UserLoggedEventHandler handler) {
        handler.onUserLoggedEvent();
    }
}
