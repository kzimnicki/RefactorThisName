package cc.explain.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * User: kzimnick
 * Date: 04.11.12
 * Time: 14:13
 */
public class AuthenticationErrorEvent extends GwtEvent<AuthenticationErrorEventHandler> {

    public static Type<AuthenticationErrorEventHandler> TYPE = new Type<AuthenticationErrorEventHandler>();

    @Override
    public Type<AuthenticationErrorEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AuthenticationErrorEventHandler handler) {
        handler.onAuthenticationErrorEvent();
    }
}
