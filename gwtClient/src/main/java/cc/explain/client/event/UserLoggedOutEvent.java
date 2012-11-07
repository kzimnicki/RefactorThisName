package cc.explain.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * User: kzimnick
 * Date: 04.11.12
 * Time: 14:13
 */
public class UserLoggedOutEvent extends GwtEvent<UserLoggedOutEventHandler> {

    public static Type<UserLoggedOutEventHandler> TYPE = new Type<UserLoggedOutEventHandler>();

    @Override
    public Type<UserLoggedOutEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(UserLoggedOutEventHandler handler) {
        handler.onUserLoggedOutEvent();
    }
}
