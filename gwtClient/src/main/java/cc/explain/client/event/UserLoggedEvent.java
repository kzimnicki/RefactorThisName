package cc.explain.client.event;

import com.google.web.bindery.event.shared.Event;

/**
 * User: kzimnick
 * Date: 04.11.12
 * Time: 14:13
 */
public class UserLoggedEvent extends Event {

    public static Type TYPE;

    @Override
    public Type getAssociatedType() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected void dispatch(Object handler) {

    }
}
