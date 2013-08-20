package cc.explain.server.core;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.type.Type;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

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

    public <T> void merge(final T bean) {
		getHibernateTemplate().merge(bean);
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


	public void executeSQL(String sqlQuery) {
		SQLQuery query = getSession().createSQLQuery(sqlQuery);
		query.executeUpdate();
	}

    public void executeHQL(String query,String name ,String value) {
        Query hqlQuery = getSession().createQuery(query);
        hqlQuery.setCacheable(true);
        if(name != null){
            hqlQuery.setString(name, value);
        }
        hqlQuery.executeUpdate();
	}

	public List getByHQL(String queryString, String[] paramNames, Object[] values) {
		return getHibernateTemplate().findByNamedParam(queryString, paramNames, values);
	}
	public List getByHQL(String queryString, String paramName, Object value) {
        Query query = getSession().createQuery(queryString);
        query.setString(paramName, (String) value);
        query.setCacheable(true);
        return  query.list();
	}

    public int countByHQL(String queryString, String paramName, Object value) {
        Query query = getSession().createQuery(queryString);
        query.setString(paramName, (String) value);
        query.setCacheable(true);
        return  ((Long)query.uniqueResult()).intValue();
	}

    public List getByHQLObject(String queryString, String paramName, Object value) {
        Query query = getSession().createQuery(queryString);
        query.setParameter(paramName, value);
        query.setCacheable(true);
        return  query.list();
	}

    public List getByHQL(String queryString, String paramName, Collection collection) {
        Query query = getSession().createQuery(queryString);
        query.setParameterList(paramName, collection);
        query.setCacheable(true);
        return  query.list();
	}

    public List getByHQL(String queryString) {
        Query query = getSession().createQuery(queryString);
        query.setCacheable(true);
        return  query.list();
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
