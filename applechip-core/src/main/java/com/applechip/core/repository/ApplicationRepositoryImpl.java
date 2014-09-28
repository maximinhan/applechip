package com.applechip.core.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.applechip.core.constant.ApplicationConstant;
import com.applechip.core.entity.Code;
import com.applechip.core.entity.CodeDetail;
import com.applechip.core.entity.QCode;
import com.applechip.core.entity.QCodeDetail;
import com.mysema.query.jpa.impl.JPAQuery;

@Repository
public class ApplicationRepositoryImpl implements ApplicationRepository {

	@PersistenceContext(unitName = ApplicationConstant.PERSISTENCE_UNIT_NAME)
	private EntityManager entityManager;

	@Override
	public List<Code> getCodeList(String codeCategoryId) {
		JPAQuery query = new JPAQuery(entityManager);
		QCode code = QCode.code;
		query.from(code).where(code.id.codeCategoryId.eq(codeCategoryId).and(code.enabled.eq(true)))
				.orderBy(code.sequence.asc());
		return query.list(code);
	}

	@Override
	public List<CodeDetail> getCodeDetails(String codeCategoryId, String codeId) {
		JPAQuery query = new JPAQuery(entityManager);
		QCodeDetail codeDetail = QCodeDetail.codeDetail;
		query.from(codeDetail)
				.where(codeDetail.id.codeId.eq(codeId).and(codeDetail.id.codeCategoryId.eq(codeCategoryId))
						.and(codeDetail.enabled.eq(true))).orderBy(codeDetail.sequence.asc());
		return query.list(codeDetail);
	}
}
