package server.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.google.gwt.user.client.rpc.IsSerializable;
import org.hibernate.util.EqualsHelper;

@MappedSuperclass
public abstract class EntityObject{

	@Id
	@GeneratedValue
	private Long id;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
	
	protected String toString(Object... objects){
		String name = this.getClass().toString();
		StringBuilder builder = new StringBuilder(" [");
		builder.append(name).append("#{");
		String delim = "";
		for(Object o : objects){
			builder.append(delim).append(o);
			delim=", ";
		}
		builder.append("}] ");
		return builder.toString();
	}

    @Override
    public int hashCode(){
        // we are working on peristed objects. From container. There is no not persisted objects!
        if(id != null){
            return id.intValue();
        }
        super.hashCode();
        return -1;

    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if ( !(obj instanceof EntityObject) ) return false;

        final EntityObject cat = (EntityObject) obj;

        if ( !cat.getId().equals( getId() ) ) return false;
        return true;
    }
}
