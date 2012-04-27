package server.api;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.type.Type;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class CommonDao extends HibernateDaoSupport {

	public <T> T find(final Class<T> beanClass, final Serializable id) {
		return getHibernateTemplate().get(beanClass, id);
	}
	
	public <T> T detach(final T bean){
		getHibernateTemplate().evict(bean);
		return bean;
	}

	public <T> List<T> getAll(final Class<T> beanClass) {
		return getHibernateTemplate().loadAll(beanClass);
	}

	public <T> T getFirstOne(final Class<T> beanClass) {
		List<T> list = getAll(beanClass);
		return list.size() > 0 ? list.get(0) : null;
	}

	public void flushChanges() {
		getHibernateTemplate().flush();
	}

	public <T> T create(final T bean) {
		getHibernateTemplate().save(bean);
		return bean;
	}
	
	public <T> void saveOrUpdate(T bean){
		getHibernateTemplate().saveOrUpdate(bean);
	}

	public <T> void delete(final T bean) {
		getHibernateTemplate().delete(bean);
	}

	public <T> void update(final T bean) {
		getHibernateTemplate().update(bean);
	}

	public void flush() {
		getHibernateTemplate().flush();
	}

	public List executeSQL(String sqlQuery, String[] names,Type[] basicTypes ) {
		 SQLQuery query = getSession().createSQLQuery(sqlQuery);
		for (int i = 0; i < basicTypes.length; i++) {
			query.addScalar(names[i], basicTypes[i]);
		}
		return query.list();
	}

	public List getByHQL(String queryString, String[] paramNames, Object[] values) {
		return getHibernateTemplate().findByNamedParam(queryString, paramNames, values);
	}
	public List getByHQL(String queryString, String paramName, Object value) {
		return getHibernateTemplate().findByNamedParam(queryString, paramName, value);
	}

    public Object getFirstByHQL(String queryString, String paramName, Object value) {
        List list = getHibernateTemplate().findByNamedParam(queryString, paramName, value);
       return list.size() > 0 ?  list.get(0) : null;
    }

     public Object getFirstByHQL(String queryString, String[] paramNames, Object[] values) {
        List list = getHibernateTemplate().findByNamedParam(queryString, paramNames, values);
       return list.size() > 0 ?  list.get(0) : null;
    }
	
}
