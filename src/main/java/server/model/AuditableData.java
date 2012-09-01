package server.model;

/**
 * User: kzimnick
 * Date: 26.08.12
 * Time: 09:46
 */
public interface AuditableData {

    public Long getId();

    public void onCreate();

    public void onUpdate();

}
