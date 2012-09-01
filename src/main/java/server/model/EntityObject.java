package server.model;

import javax.persistence.*;

import com.google.common.base.Objects;
import com.google.gwt.user.client.rpc.IsSerializable;
import org.hibernate.util.EqualsHelper;

import java.util.Date;

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
        return Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof EntityObject){
            final EntityObject other = (EntityObject) obj;
            return Objects.equal(id, other.getId());
        } else{
            return false;
        }
    }
}
