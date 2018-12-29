package demo.dao;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import org.hibernate.PersistentObjectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import demo.constant.Constants;

@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public abstract class BaseDao<T> implements Dao<T> {
	private static Logger log = LoggerFactory.getLogger("demo.dao.BaseDao");
	@PersistenceContext
	private EntityManager em;

	public EntityManager getEntityManager() {
		return em;
	}

	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	public BaseDao() {

	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public T getEntityById(Class<T> entityModelClass, Long id) {
		return em.find(entityModelClass, id);
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	@Cacheable
	public List<T> getAll(Class<T> entityModelClass) throws Exception {
		String nameMethod = "getAll";
		LocalTime start = LocalTime.now();
		log.debug(nameMethod + Constants.TWO_PARAMETERS, Constants.ENTITY, entityModelClass, Constants.BEGIN, start);
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(entityModelClass);
		Root<T> rootEntry = cq.from(entityModelClass);
		CriteriaQuery<T> all = cq.select(rootEntry);
		TypedQuery<T> allQuery = em.createQuery(all);
		List<T> result = allQuery.getResultList();

		LocalTime end = LocalTime.now();
		Duration resultTime = Duration.between(end, start);
		log.debug(nameMethod + Constants.THREE_PARAMETERS, Constants.ENTITY, entityModelClass, "end", end, "resultTime",
				resultTime);
		return result;

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public T update(T entityModel) throws Exception {
		return em.merge(entityModel);
	}

	// MANDATORY: Transaction must be created before.
	@Transactional(propagation = Propagation.MANDATORY)
	@Override
	public void add(T entityModel) {
		String nameMethod = "add";
		try {
			em.persist(entityModel);
		} catch (PersistentObjectException e) {
			log.debug(nameMethod + Constants.TWO_PARAMETERS, Constants.ENTITY, entityModel, Constants.ERROR,
					e);

		}
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	@Override
	public boolean addAll(List<T> entityModelList) {
		String nameMethod = "addAll";
		LocalTime start = LocalTime.now();
		String entity = "empty";
		if(!entityModelList.isEmpty()) {
			entity = entityModelList.get(0).getClass().toString();
		}
		log.debug(nameMethod + Constants.TWO_PARAMETERS, Constants.ENTITY,entity, Constants.BEGIN,
				start);
		boolean result = true;
		for (T element : entityModelList) {
			add(element);
		}
		LocalTime end = LocalTime.now();
		Duration resultTime = Duration.between(end, start);
		log.debug(nameMethod + Constants.THREE_PARAMETERS, Constants.ENTITY, entity, "end", end,
				"resultTime", resultTime);
		return result;
	}

	@Transactional
	@Override
	public void remove(Class<T> entityModelClass, Long id) throws Exception {
		T obj = getEntityById(entityModelClass, id);
		remove(obj);
	}

	@Transactional
	@Override
	public void remove(T entityModel) throws Exception {
		em.remove(entityModel);

	}


	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public List<T> findEntityByAttributeOfString(Class<T> entityModelClass, String columnName, String columnValue)
			throws Exception {

		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityModelClass);
		Root<T> root = criteriaQuery.from(entityModelClass);
		criteriaQuery.select(root);

		ParameterExpression<String> params = criteriaBuilder.parameter(String.class);
		criteriaQuery.where(criteriaBuilder.equal(root.get(columnName), params));

		TypedQuery<T> query = em.createQuery(criteriaQuery);
		query.setParameter(params, columnValue);

		return query.getResultList();
	}
}
