package com.dwarfeng.jier.mh4w.core.model.struct;

import java.util.Map;
import java.util.Objects;

/**
 * Ĭ��ͳ�ƽ����
 * <p> ͳ�ƽ����Ĭ��ʵ�֡�
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class DefaultCountResult implements CountResult {

	private final Person person;
	private final double equivalentWorkTime;
	private final double originalWorkTime;
	private final double workticket;
	private final Map<Job, Double> workticketMap;
	private final Map<Job, Double> workticketPercentMap;
	private final double value;
	
	/**
	 * ��ʵ����
	 * @param person ָ������Ա��
	 * @param equivalentWorkTime ָ���ĵ�Ч��ʱ��
	 * @param originalWorkTime ָ����ԭʼ��ʱ��
	 * @param workticket ��Ʊ��ʱ�䡣
	 * @param workticketMap ָ���Ĺ�Ʊ��ʱӳ�䡣
	 * @param workticketPercentMap ָ���Ĺ�Ʊ��ʱ�ٷֱ�ӳ�䡣
	 * @param value ָ����Ӧ�ý�
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public DefaultCountResult(Person person, double equivalentWorkTime, double originalWorkTime, double workticket, 
			Map<Job, Double> workticketMap, Map<Job, Double> workticketPercentMap, double value) {
		Objects.requireNonNull(person, "��ڲ��� person ����Ϊ null��");
		Objects.requireNonNull(workticketMap, "��ڲ��� workticketMap ����Ϊ null��");
		Objects.requireNonNull(workticketPercentMap, "��ڲ��� workticketPercentMap ����Ϊ null��");

		this.person = person;
		this.equivalentWorkTime = equivalentWorkTime;
		this.originalWorkTime = originalWorkTime;
		this.workticket = workticket;
		this.workticketMap = workticketMap;
		this.workticketPercentMap = workticketPercentMap;
		this.value = value;
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
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.CountResult#getWorkticket(com.dwarfeng.jier.mh4w.core.model.struct.Job)
	 */
	@Override
	public double getWorkticket(Job job) {
		return workticketMap.getOrDefault(job, 0.0);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.CountResult#getWorkticketPercent(com.dwarfeng.jier.mh4w.core.model.struct.Job)
	 */
	@Override
	public double getWorkticketPercent(Job job) {
		return workticketPercentMap.getOrDefault(job, 0.0);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.CountResult#getValue()
	 */
	@Override
	public double getValue() {
		return value;
	}

}
