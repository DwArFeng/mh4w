package com.dwarfeng.jier.mh4w.core.model.struct;

import java.util.Map;
import java.util.Objects;

/**
 * Ĭ��ͳ�ƽ����
 * <p> ͳ�ƽ����Ĭ��ʵ�֡�
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public final class DefaultCountResult implements CountResult {

	private final Person person;
	private final double equivalentWorkTime;
	private final double equivalentWorkTimeOffset;
	private final double originalWorkTime;
	private final double workticket;
	private final double equivalentWorkticket;
	private final Map<Job, Double> workticketMap;
	private final Map<Job, Double> equivalentWorkticketMap;
	
	/**
	 * ��ʵ����
	 * @param person ָ������Ա��
	 * @param equivalentWorkTime ָ���ĵ�Ч��ʱ��
	 * @param equivalentWorkTimeOffset ��Ч��ʱ������
	 * @param originalWorkTime ָ����ԭʼ��ʱ��
	 * @param workticket ��Ʊ��ʱ�䡣
	 * @param equivalentWorkticket ��Ч��Ʊʱ�䡣
	 * @param workticketMap ָ���Ĺ�Ʊ��ʱӳ�䡣
	 * @param workticketPercentMap ָ���Ĺ�Ʊ��ʱ�ٷֱ�ӳ�䡣
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public DefaultCountResult(Person person, double equivalentWorkTime, double equivalentWorkTimeOffset,
			double originalWorkTime, double workticket, double equivalentWorkticket, Map<Job, Double> workticketMap,
			Map<Job, Double> equivalentWorkticketMap) {
		Objects.requireNonNull(person, "��ڲ��� person ����Ϊ null��");
		Objects.requireNonNull(workticketMap, "��ڲ��� workticketMap ����Ϊ null��");
		Objects.requireNonNull(equivalentWorkticketMap, "��ڲ��� equivalentWorkticketMap ����Ϊ null��");

		this.person = person;
		this.equivalentWorkTime = equivalentWorkTime;
		this.equivalentWorkTimeOffset = equivalentWorkTimeOffset;
		this.originalWorkTime = originalWorkTime;
		this.workticket = workticket;
		this.equivalentWorkticket = equivalentWorkticket;
		this.workticketMap = workticketMap;
		this.equivalentWorkticketMap = equivalentWorkticketMap;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.DataWithPerson#getPerson()
	 */
	@Override
	public Person getPerson() {
		return person;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.CountResult#getEquivalentWorkTime()
	 */
	@Override
	public double getEquivalentWorkTime() {
		return equivalentWorkTime;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.CountResult#getEquivalentWorkTimeOffset()
	 */
	@Override
	public double getEquivalentWorkTimeOffset() {
		return equivalentWorkTimeOffset;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.CountResult#getOriginalWorkTime()
	 */
	@Override
	public double getOriginalWorkTime() {
		return originalWorkTime;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.CountResult#getWorkticket()
	 */
	@Override
	public double getWorkticket() {
		return workticket;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.CountResult#getEquivalentWorkticket()
	 */
	@Override
	public double getEquivalentWorkticket() {
		return equivalentWorkticket;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.CountResult#getWorkticket(com.dwarfeng.jier.mh4w.core.model.struct.Job)
	 */
	@Override
	public double getWorkticket(Job job) {
		return workticketMap.getOrDefault(job, 0.0);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.CountResult#getEquivalentWorkticket(com.dwarfeng.jier.mh4w.core.model.struct.Job)
	 */
	@Override
	public double getEquivalentWorkticket(Job job) {
		return equivalentWorkticketMap.getOrDefault(job, 0.0);
	}

}
