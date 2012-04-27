package server.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.google.gwt.user.client.rpc.IsSerializable;

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
}
